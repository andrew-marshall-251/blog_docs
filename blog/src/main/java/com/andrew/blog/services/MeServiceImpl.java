package com.andrew.blog.services;

import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import org.springframework.security.core.Authentication;

public class MeServiceImpl implements MeService {
	@Override
	public SelfResponse getSelf(Authentication auth) {
		return null;
	}

	@Override
	public SelfResponse updateSelf(Authentication auth) {
		return null;
	}

	@Override
	public void deleteSelf(Authentication auth) {

	}

	@Override
	public SelfResponse updateSelfPassword(Authentication auth) {
		return null;
	}

	@Override
	public PostListResponse getSelfPosts(Authentication auth) {
		return null;
	}
}
