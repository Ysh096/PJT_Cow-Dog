package com.xy.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.EachOther;
import com.xy.entity.Follow;
import com.xy.repository.FollowRepository;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	FollowRepository followRepo;
	@PersistenceContext
	EntityManager em;

	@Override
	public Follow save(Follow follow) {

		return followRepo.save(follow);
	}

	@Override
	public List<EachOther> getFollowerMemberId(long id) {

		ArrayList<EachOther> followEachOther = new ArrayList<EachOther>();

		// 현재 들어온 id를 팔로우 하고 있는 사람들
		String jpql1 = "select distinct f.follower_id, f.member_id from Follow as f where f.member_id=:id";
		Query query1 = em.createQuery(jpql1);
		query1.setParameter("id", id);
		List<EachOther> follower1 = new ArrayList<EachOther>();

		List queryResult1 = query1.getResultList();

		for (Object o : queryResult1) {
			Object[] result = (Object[]) o;
			follower1.add(new EachOther((long) result[0], (long) result[1]));
			System.out.println("follower_id " + result[0]);
			System.out.println("memeber_id " + result[1]);
		}
		System.out.println("나를 팔로우 하고 있는 사람들: " + follower1.toString());

		// 현재 들어온 아이디가 follow 하는 사람들
		String jpql2 = "select distinct f.follower_id, f.member_id from Follow as f where f.follower_id=:id";
		Query query2 = em.createQuery(jpql2);
		query2.setParameter("id", id);
		List<EachOther> follower2 = new ArrayList<EachOther>();

		List queryResult2 = query2.getResultList();

		for (Object o : queryResult2) {
			Object[] result = (Object[]) o;
			follower2.add(new EachOther((long) result[0], (long) result[1]));
			System.out.println("follower_id " + result[0]);
			System.out.println("memeber_id " + result[1]);
		}

		System.out.println("내가 팔로우 하고 있는 사람들" + follower2.toString());

		// 서로 팔로우를 하고 있는 사람들 골라낸다
		for (int i = 0; i < follower1.size(); i++) {
			for (int j = 0; j < follower2.size(); j++) {
				if ((follower1.get(i).getMember_id() == follower2.get(j).getFollower_Id())
						&& follower1.get(i).getFollower_Id() == follower2.get(j).getMember_id()) {
					followEachOther.add(follower1.get(i));
				}
			}
		}

		System.out.println("나를 팔로우 하고 있는 사람들:      " + follower1.toString());
		System.out.println("서로 팔로우를 하고 있는 사람들:      " + followEachOther.toString());

		ArrayList<EachOther> resultList = new ArrayList<EachOther>();

		for (int i = 0; i < follower1.size(); i++) {
			boolean flag = false;
			for (int j = 0; j < followEachOther.size(); j++) {
				if (follower1.get(i).getMember_id() == followEachOther.get(j).getMember_id()
						&& follower1.get(i).getFollower_Id() == followEachOther.get(j).getFollower_Id()) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				resultList.add(follower1.get(i));
			}
		}

		System.out.println(resultList.toString());

		return resultList;
	}

	@Override
	public List<EachOther> getFollowEachOther(long id) {
		// 맞팔이 아닌 자신을 팔로우 하는 사람들

		ArrayList<EachOther> resultList = new ArrayList<EachOther>();

		// 현재 들어온 id를 팔로우 하고 있는 사람들
		String jpql1 = "select distinct f.follower_id, f.member_id from Follow as f where f.member_id=:id";
		Query query1 = em.createQuery(jpql1);
		query1.setParameter("id", id);
		List<EachOther> follower1 = new ArrayList<EachOther>();

		List queryResult1 = query1.getResultList();

		for (Object o : queryResult1) {
			Object[] result = (Object[]) o;
			follower1.add(new EachOther((long) result[0], (long) result[1]));
			System.out.println("follower_id " + result[0]);
			System.out.println("memeber_id " + result[1]);
		}
		System.out.println("나를 팔로우 하고 있는 사람들: " + follower1.toString());

		// 현재 들어온 아이디가 follow 하는 사람들
		String jpql2 = "select distinct f.follower_id, f.member_id from Follow as f where f.follower_id=:id";
		Query query2 = em.createQuery(jpql2);
		query2.setParameter("id", id);
		List<EachOther> follower2 = new ArrayList<EachOther>();

		List queryResult2 = query2.getResultList();

		for (Object o : queryResult2) {
			Object[] result = (Object[]) o;
			follower2.add(new EachOther((long) result[0], (long) result[1]));
			System.out.println("follower_id " + result[0]);
			System.out.println("memeber_id " + result[1]);
		}

		System.out.println("내가 팔로우 하고 있는 사람들" + follower2.toString());

		for (int i = 0; i < follower1.size(); i++) {
			for (int j = 0; j < follower2.size(); j++) {
				if ((follower1.get(i).getMember_id() == follower2.get(j).getFollower_Id())
						&& follower1.get(i).getFollower_Id() == follower2.get(j).getMember_id()) {
					resultList.add(follower1.get(i));
				}
			}
		}

		System.out.println(resultList.toString());

		return resultList;
	}

	
	@Override
	public List<Follow> amIFollowed(long id) {
		System.out.println("팔로우 하는 사"+id);
		String jpql="select distinct f from Follow as f where f.follower_id=:id";
		TypedQuery<Follow> query=em.createQuery(jpql, Follow.class);
		query.setParameter("id", id);
		System.out.println("내가 팔로우 하는 사람들:   "+query.getResultList().toString());
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int unFollow(long memberid, long followid) {
		System.out.println("로그인 한 사람:   "+followid);
		System.out.println("언팔을 당하는 사람:   "+memberid);
		//followid : 로그인 한 사람,  memberid: 언팔을 당하는 사람
		String jpql="delete from Follow m where m.member_id=:memberid and m.follower_id=:followid";
		Query query = em.createQuery(jpql).setParameter("memberid", memberid).setParameter("followid", followid);
		int rows = query.executeUpdate();
		return rows;
	}
	
	
	
	

}
