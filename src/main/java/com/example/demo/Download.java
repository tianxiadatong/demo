/*
package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;

public class Download {
    */
/**
     * 下载抖音无水印视频
     *
     * @throws IOException
     *//*

    @GetMapping(value = "/downloadDy")
    public void downloadDy(String dyUrl, HttpServletResponse response) throws IOException {
        ResultDto resultDto = new ResultDto();
        try {
            dyUrl = URLDecoder.decode(dyUrl).replace("dyUrl=", "");
            resultDto = dyParseUrl(dyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultDto.getVideoUrl().contains("http://")) {
            resultDto.setVideoUrl(resultDto.getVideoUrl().replace("http://", "https://"));
        }

        String videoUrl = resultDto.getVideoUrl();

        response.sendRedirect(videoUrl);
    }

    public ResultDto dyParseUrl(String redirectUrl) throws Exception {

        redirectUrl = CommonUtils.getLocation(redirectUrl);
        ResultDto dyDto = new ResultDto();

        if (!StringUtils.isEmpty(redirectUrl)) {
            */
/**
             * 1、用 ItemId 拿视频的详细信息，包括无水印视频url
             *//*

            String itemId = CommonUtils.matchNo(redirectUrl);

            StringBuilder sb = new StringBuilder();
            sb.append(CommonUtils.DOU_YIN_BASE_URL).append(itemId);

            String videoResult = CommonUtils.httpGet(sb.toString());

            DYResult dyResult = JSON.parseObject(videoResult, DYResult.class);

            */
/**
             * 2、无水印视频 url
             *//*

            String videoUrl = dyResult.getItem_list().get(0)
                    .getVideo().getPlay_addr().getUrl_list().get(0)
                    .replace("playwm", "play");
            String videoRedirectUrl = CommonUtils.getLocation(videoUrl);

            dyDto.setVideoUrl(videoRedirectUrl);
            */
/**
             * 3、音频 url
             *//*

            String musicUrl = dyResult.getItem_list().get(0).getMusic().getPlay_url().getUri();
            dyDto.setMusicUrl(musicUrl);
            */
/**
             * 4、封面
             *//*

            String videoPic = dyResult.getItem_list().get(0).getVideo().getDynamic_cover().getUrl_list().get(0);
            dyDto.setVideoPic(videoPic);

            */
/**
             * 5、视频文案
             *//*

            String desc = dyResult.getItem_list().get(0).getDesc();
            dyDto.setDesc(desc);
        }
        return dyDto;
    }
}
*/
