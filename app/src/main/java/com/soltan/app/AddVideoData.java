package com.soltan.app;

public class AddVideoData {
    private String video_name;
    private String playList_name;
    private String Video_id;
    private String audio_name;
    private String audio_url;
    private String pdf_url;
    private String pdf_name;
    public AddVideoData(){

    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }
//    public String getVideo_name() {
//        return video_name;
//    }

//    public void setVideo_name(String video_name) {
//        this.video_name = video_name;
//    }

    public String getPlayList_name() {
        return playList_name;
    }

    public void setPlayList_name(String playList_name) {
        this.playList_name = playList_name;
    }

    public void setVideo_id(String video_id) {
        Video_id = video_id;
    }



    public String getAudio_name() {
        return audio_name;
    }

    public void setAudio_name(String audio_name) {
        this.audio_name = audio_name;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public void setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
    }

    public String getVideo_id() {
        return Video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }
}
