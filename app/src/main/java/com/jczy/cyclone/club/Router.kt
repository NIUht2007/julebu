package com.jczy.cyclone.club

object Router {
    // 主页面
    const val MAIN_ACTIVITY = "/app/MainActivity"
    
    // 玩车圈
    const val MOTO_CIRCLE_FRAGMENT = "/motocircle/MotoCircleFragment"
    const val POST_DETAIL_ACTIVITY = "/motocircle/PostDetailActivity"
    const val ARTICLE_DETAIL_ACTIVITY = "/motocircle/ArticleDetailActivity"
    const val GUIDE_DETAIL_ACTIVITY = "/motocircle/GuideDetailActivity"
    
    // 俱乐部
    const val CLUB_FRAGMENT = "/club/ClubFragment"
    const val CLUB_HOME_FRAGMENT_V2 = "/club/ClubHomeFragmentV2"
    const val CLUB_CREATE_ACTIVITY = "/club/ClubCreateActivity"
    const val CLUB_EDIT_ACTIVITY = "/club/ClubEditActivity"
    const val CLUB_ACTIVITY_DETAIL_ACTIVITY = "/club/ClubActivityDetailActivity"
    
    // 商城
    const val MALL_FRAGMENT = "/mall/MallFragment"
    const val GOODS_DETAIL_ACTIVITY = "/mall/GoodsDetailActivity"
    const val SHOPPING_CAR_ACTIVITY = "/mall/ShoppingCarActivity"
    const val ORDER_CONFIRM_ACTIVITY = "/mall/OrderConfirmActivity"
    const val ORDER_LIST_ACTIVITY = "/mall/OrderListActivity"
    const val ORDER_DETAIL_ACTIVITY = "/mall/OrderDetailActivity"
    
    // 消息
    const val MESSAGE_FRAGMENT = "/message/MessageFragment"
    
    // 个人中心
    const val MINE_FRAGMENT = "/mine/MineFragment"
    const val PROFILE_EDIT_ACTIVITY = "/mine/ProfileEditActivity"
    const val MY_GARAGE_ACTIVITY = "/mine/MyGarageActivity"
    const val FLOW_RECHARGE_ACTIVITY = "/mine/FlowRechargeActivity"
    const val STOLEN_APPLY_ACTIVITY = "/mine/StolenApplyActivity"
    const val AFTER_SALES_ACTIVITY = "/mine/AfterSalesActivity"
    const val BOOKING_ACTIVITY = "/mine/BookingActivity"
    const val STORE_LIST_ACTIVITY = "/mine/StoreListActivity"
    
    // 即时通讯
    const val CHAT_P2P_ACTIVITY = "/im/MyChatP2PActivity"
    const val CHAT_TEAM_ACTIVITY = "/im/MyChatTeamActivity"
    
    // 轨迹
    const val TRAJECTORY_LIST_ACTIVITY = "/trajectory/TrajectoryListActivity"
    const val TRA_PLAY_ACTIVITY = "/trajectory/TraPlayActivity"
    
    // 路径攻略
    const val ROUTE_LIST_ACTIVITY = "/route/RouteListActivity"
    const val ROUTE_CREATE_ACTIVITY = "/route/RouteCreateActivity"
    const val ROUTE_NAVI_ACTIVITY = "/route/RouteNaviActivity"
    
    // 好友
    const val FRIENDS_LIST_ACTIVITY = "/friends/FriendsListActivity"
    const val NEARBY_PEOPLE_ACTIVITY = "/friends/NearbyPeopleActivity"
    
    // 设备绑定
    const val SCAN_ACTIVITY = "/bind/ScanActivity"
    const val BIND_PLATE_NUMBER_ACTIVITY = "/bind/BindPlateNumber"
    const val BIND_DEVICE_NUM_ACTIVITY = "/bind/BindDeviceNum"
    const val BIND_BATTERY_NUM_ACTIVITY = "/bind/BindBatteryNum"
    
    // 三方服务
    const val TY_H5_GAS_WEB_ACTIVITY = "/third/TYH5GasWebActivity"
    const val COMMON_WEB_ACTIVITY = "/third/CommonWebActivity"
}