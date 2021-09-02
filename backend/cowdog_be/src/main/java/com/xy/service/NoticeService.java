package com.xy.service;

import java.util.List;

import com.xy.api.request.NoticeCreatePostReq;
import com.xy.api.request.NoticeUpdatePutReq;
import com.xy.entity.Notice;

public interface NoticeService {
    public String createNotice(NoticeCreatePostReq noticereq);
    public void updateNotice(NoticeUpdatePutReq noticeUpdateReq);
    public List<Notice> getNoticeList();
    public void deleteNotice(Long noticecNo);
}
