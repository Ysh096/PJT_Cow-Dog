package com.xy.service;
import java.util.List;

import com.xy.api.request.UserReportPostReq;
import com.xy.entity.UserReport;

public interface UserReportService {
    public String createUserReport(UserReportPostReq request);
    public List<UserReport> getUserReportList();
    public void deleteUserReport(Long userReportNo);
    public void deleteReportedUser(String userId);
	public int suspendReportedUser(String userId);
}