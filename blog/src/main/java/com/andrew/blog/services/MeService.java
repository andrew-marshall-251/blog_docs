package com.andrew.blog.services;

import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import org.springframework.security.core.Authentication;

public interface MeService {
	SelfResponse getSelf(Authentication auth);
	SelfResponse updateSelf(Authentication auth);
	void deleteSelf(Authentication auth);
	SelfResponse updateSelfPassword(Authentication auth);
	PostListResponse getSelfPosts(Authentication auth);
}
