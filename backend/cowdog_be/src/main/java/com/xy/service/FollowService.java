package com.xy.service;

import java.util.List;

import com.xy.entity.EachOther;
import javax.persistence.Query;

import com.xy.entity.Follow;

public interface FollowService {
	public Follow save(Follow follow);
	public List<EachOther> getFollowerMemberId(long id);
	public List<EachOther> getFollowEachOther(long id);
	public List<Follow> amIFollowed(long id);
	public int unFollow(long memberid, long followid);
}
