package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.UpdateSelfPasswordRequest;
import com.andrew.blog.dtos.requests.UpdateSelfRequest;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import com.andrew.blog.dtos.responses.UpdateSelfResponse;

public interface MeService {
	SelfResponse getSelf(String username);
	UpdateSelfResponse updateSelf(UpdateSelfRequest request, String username);
	void deleteSelf(String username);
	UpdateSelfResponse updateSelfPassword(UpdateSelfPasswordRequest request, String username);
	PostListResponse getSelfPosts(String username);
}
