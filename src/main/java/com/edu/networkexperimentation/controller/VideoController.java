package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.contant.UserConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Video;
import com.edu.networkexperimentation.model.response.ResponseUser;
import com.edu.networkexperimentation.model.response.ResponseVideo;
import com.edu.networkexperimentation.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("video")
@Slf4j
@CrossOrigin
public class VideoController {
    @Resource
    private VideoService videoService;

    @GetMapping("/all")
    public BaseResponse<List<ResponseVideo>> getAllVideo() {
        return ResultUtils.success(videoService.getAllVideo());
    }

    @PostMapping("/upload")
    public BaseResponse<Long> materialFileUpload(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("section") Long sectionID,
                                                 HttpServletRequest request) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未上传文件");
        }

        ResponseUser user = (ResponseUser) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
//        log.info("用户是否有权限: " + (user == null ? "未登录" : user.getUserIdentity() == UserConstant.ADMIN_ROLE ? "有权限" : "无权限"));
        if (user == null || user.getUserIdentity() != UserConstant.ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        if (!file.getContentType().contains("video")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "你上传的不是视频");
        }

        Long uploadResult = videoService.upload(file, title, user.getId(), sectionID);
        return ResultUtils.success(uploadResult);
//        return ResultUtils.success(file.getContentType());
    }

    @GetMapping("/info/latest")
    public BaseResponse<ResponseVideo> getLatestVideoInfo() {
        return ResultUtils.success(videoService.getLatestVideo());
    }

    @GetMapping("/info/{id}")
    public BaseResponse<ResponseVideo> getFile(@PathVariable Long id) {
        Video file = videoService.getById(id);
        if (file == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "无该文件");
        }
        return ResultUtils.success(new ResponseVideo(file));
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Video video = videoService.getById(id);
        BufferedInputStream bis = null;
        try {
            File file = new File(video.getFile());
            if (file.exists()) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
                long fileLength;
                String rangBytes = "";
                fileLength = file.length();

                // get file content
                InputStream ins = new FileInputStream(file);
                bis = new BufferedInputStream(ins);

                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) { // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", String.valueOf(contentLength));

                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(String.valueOf(p).toString()).append("-")
                            .append(String.valueOf(fileLength - 1)).append("/")
                            .append(String.valueOf(fileLength)).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + String.valueOf(fileLength);
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
                            .append(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }

                String fileName = file.getName();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

                OutputStream out = response.getOutputStream();
                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
            }
        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteMaterial(@PathVariable Long id, HttpServletRequest request) {
        boolean flag = videoService.deleteVideo(id);
        return ResultUtils.success(flag);
    }
}
