package com.jczy.cyclone.club.flow_eventbus

// 登录成功事件
class LoginEvent(val userId: String, val token: String)

// 退出登录事件
class LogoutEvent

// Token 失效事件
class TokenInvalidEvent