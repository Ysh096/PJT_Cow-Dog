# JPA DELETE

[참고1 - 스택오버플로우](https://stackoverflow.com/questions/40062479/jpa-delete-query-not-working)

[참고2 - 네이버블로그](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=ydkun2&logNo=221706359126)



언팔로우를 구현하기 위해 JPA query를 이용하여 delete 문을 만들었다. 우선 controller에는 다음과 같은 함수를 적었다.

```java
	@DeleteMapping("/follow/{member_id}/{follow_id}")
	public List<Follow> Unfollow(@PathVariable("member_id") long memberid, @PathVariable("follow_id") long followid) {
		System.out.println("삭제 시작합니다?");
		followSer.unFollow(memberid, followid);
		return followSer.amIFollowed(memberid);
	}
```

프론트에서 로그인한 사용자의 아이디인 member_id와 언팔로우 하려는 대상인 follow_id를 가지고 와서 unFollow 동작을 실행하기 위해 이용한다.

unFollow가 끝나면 현재 로그인한 사용자 member_id가 팔로우한 사용자를 가져오기 위해 amIFollowed를 실행한다.



FollowService.java는 다음과 같다.

```java
package com.xy.service;

import java.util.List;

import javax.persistence.Query;

import com.xy.entity.Follow;

public interface FollowService {
	public Follow save(Follow follow);
	public List<Follow> getMemberId(long id);
	public List<Follow> amIFollowed(long id);
	public int unFollow(long memberid, long followid);
}
```

FollowServiceImpl.java는 다음과 같다.

```java
package com.xy.service;

import java.util.List;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.Follow;
import com.xy.repository.FollowRepository;

@Service
public class FollowServiceImpl implements FollowService{
	
	@Autowired
	FollowRepository followRepo;
	@PersistenceContext
    EntityManager em;

	@Override
	public Follow save(Follow follow) {
		
		
		
		return followRepo.save(follow);
	}

	@Override
	public List<Follow> getMemberId(long id) {
		
		//follower_id== id 인 경우
		
		
		//자신을 팔로우 하는 사람들
		String jpql="select distinct f from Follow as f where f.follower_id=:id";
		TypedQuery<Follow> query=em.createQuery(jpql,Follow.class);
		query.setParameter("id", id);

		return query.getResultList();
	}
	
	@Override
	public List<Follow> amIFollowed(long id) {
		String jpql="select distinct f from Follow as f where f.member_id=:id";
		TypedQuery<Follow> query=em.createQuery(jpql, Follow.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int unFollow(long memberid, long followid) {
		String jpql="delete from Follow m where m.member_id=:memberid and m.follower_id=:followid";
		Query query = em.createQuery(jpql).setParameter("memberid", memberid).setParameter("followid", followid);
		int rows = query.executeUpdate();
		return rows;
	}
	
```

맨 밑의 unFollow 부분만 살펴보자.

- createQuery는 말 그대로 jpql(Java Persistence Query Language)로 적은 것을 DB에서 사용하는 쿼리문으로 바꿔주는 역할을 한다.
- setParameter는 바인딩 형태로 적어놓은 memberid나 followid를 unFollow 함수가 가진 long memberid, long followid와 연결해주는 역할을 한다.
  - **setParameter("바인딩한 이름", 변수이름)** 으로 쓴다. 
  - 예를 들어  `"delete from Follow m where m.member_id=:memberid and m.follower_id=:followid"` 라면, DB의 member_id 컬럼에 memberid라는 이름의 변수를 매핑시키는 것이고, 만약 `unFollow(long member-id, long follow-id)` 처럼 받아왔다면 다음과 같이 setParameter를 구성한다.
  - `setParameter("memberid", member-id).setParameter("followid", follow-id)`

오류는 크게 세 가지 발생했다.

1) 처음에 Follow 동작을 보고 `TypedQuery<Follow> query=em.createQuery(jpql,Follow.class);`를 복붙하여 사용했는데, 이는 select인 경우에만 사용할 수 있는 것이었다. delete가 반환하는 것은 Follow 형태가 아니라 그냥 쿼리이다. 따라서 `Query query = em.createQuery(jpql)~~` 이런 식으로 작성해야 한다.

2) delete 쿼리를 만들었다고 해서 실행되지는 않는다. 

   `return em.createQuery(jpql).setParameter("memberid", memberid).setParameter("followid", followid);`

   위와 같이 return 할 때 적어줘도 마찬가지이다. 따로 실행해주는 과정이 필요하다. 이를 위해 query.executeUpdate()를 사용하였다. rows는 제거한 행의 수를 받은 값이라고 한다.

3) 여기까지 하면 update / delete 어쩌구 하는 오류가 뜨는데, 이는 @Transactional 어노테이션을 붙여주면 해결된다. 참고2에 나와있다.



예상과는 달리 쿼리문에는 문제가 없었다. 아래는 오류가 발생한 코드다.



## 오류 코드

```java
@Override
public void unFollow(long memberid, long followid) {
    String jpql="delete from Follow as m where m.member_id=:memberid AND m.follower_id:=followid";
    TypedQuery<Follow> query=em.createQuery(jpql, Follow.class);
    query.setParameter("memberid", memberid);
    query.setParameter("followid", followid);
}
```

1)타입을 잘못 지정함



```java
@Override
public Query unFollow(long memberid, long followid) {
    System.out.println("삭제해보자~");
    String jpql="delete from Follow m where m.member_id=:memberid and m.follower_id=:followid";
    return em.createQuery(jpql).setParameter("memberid", memberid).setParameter("followid", followid);
}
```

2)