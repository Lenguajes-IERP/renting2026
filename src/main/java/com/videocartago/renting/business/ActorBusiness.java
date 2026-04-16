package com.videocartago.renting.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videocartago.renting.data.ActorData;
import com.videocartago.renting.domain.Actor;

@Service
public class ActorBusiness {
	@Autowired
	private ActorData actorData;
	
	 public List<Actor> findAll() {
		 return actorData.findAll();
	 }

}
