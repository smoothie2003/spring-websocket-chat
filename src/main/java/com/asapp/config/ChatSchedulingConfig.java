package com.asapp.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class ChatSchedulingConfig implements SchedulingConfigurer {
	
	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		return new ThreadPoolTaskScheduler();
	}
	
	@Bean
	@Inject
	public ActiveUserPinger activeUserPinger(SimpMessagingTemplate template, ActiveUserService activeUserService) {
		return new ActiveUserPinger(template, activeUserService);
	}
	@Override
	public void configureTasks(ScheduledTaskRegistrar arg0) {
		arg0.setTaskScheduler(taskScheduler());
	}

}
