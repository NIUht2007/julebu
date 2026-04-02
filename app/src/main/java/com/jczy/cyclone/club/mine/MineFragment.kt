package com.jczy.cyclone.club.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.common.BaseFragment
import com.jczy.cyclone.club.common.GlideEngine
import com.jczy.cyclone.club.flow_eventbus.EventBus
import com.jczy.cyclone.club.flow_eventbus.LogoutEvent
import com.jczy.cyclone.club.mmkv.AuthMMKV
import com.jczy.cyclone.club.mmkv.UserMMKV
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MineFragment : BaseFragment() {

    private lateinit var tvUsername: TextView
    private lateinit var tvUserId: TextView
    private lateinit var ivAvatar: ImageView
    private lateinit var userInfoSection: View
    private lateinit var rlMyGarage: RelativeLayout
    private lateinit var rlFlowRecharge: RelativeLayout
    private lateinit var rlStolenReport: RelativeLayout
    private lateinit var rlAfterSales: RelativeLayout
    private lateinit var rlBooking: RelativeLayout
    private lateinit var rlStore: RelativeLayout
    private lateinit var rlCareService: RelativeLayout
    private lateinit var rlFixOnline: RelativeLayout
    private lateinit var rlOneClickRescue: RelativeLayout
    private lateinit var rlMessageSettings: RelativeLayout
    private lateinit var rlBindWechat: RelativeLayout
    private lateinit var rlChangePassword: RelativeLayout
    private lateinit var rlLogout: RelativeLayout
    private lateinit var rlWiki: RelativeLayout
    private lateinit var rlFaq: RelativeLayout
    private lateinit var rlShare: RelativeLayout
    private lateinit var rlCarUsage: RelativeLayout

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView(view: View) {
        tvUsername = view.findViewById(R.id.tv_username)
        tvUserId = view.findViewById(R.id.tv_user_id)
        ivAvatar = view.findViewById(R.id.iv_avatar)
        userInfoSection = view.findViewById(R.id.user_info_section)
        rlMyGarage = view.findViewById(R.id.rl_my_garage)
        rlFlowRecharge = view.findViewById(R.id.rl_flow_recharge)
        rlStolenReport = view.findViewById(R.id.rl_stolen_report)
        rlAfterSales = view.findViewById(R.id.rl_after_sales)
        rlBooking = view.findViewById(R.id.rl_booking)
        rlStore = view.findViewById(R.id.rl_store)
        rlCareService = view.findViewById(R.id.rl_care_service)
        rlFixOnline = view.findViewById(R.id.rl_fix_online)
        rlOneClickRescue = view.findViewById(R.id.rl_one_click_rescue)
        rlMessageSettings = view.findViewById(R.id.rl_message_settings)
        rlBindWechat = view.findViewById(R.id.rl_bind_wechat)
        rlChangePassword = view.findViewById(R.id.rl_change_password)
        rlLogout = view.findViewById(R.id.rl_logout)
        rlWiki = view.findViewById(R.id.rl_wiki)
        rlFaq = view.findViewById(R.id.rl_faq)
        rlShare = view.findViewById(R.id.rl_share)
        rlCarUsage = view.findViewById(R.id.rl_car_usage)

        setupClickListeners()
    }

    override fun initData() {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val userId = UserMMKV.userId ?: ""
        val username = UserMMKV.username ?: "用户名"
        tvUsername.text = username
        tvUserId.text = "ID: $userId"
        loadAvatar()
    }

    private fun loadAvatar() {
        val avatarUrl = UserMMKV.avatar
        if (avatarUrl.isNullOrEmpty()) {
            // 使用默认头像
            ivAvatar.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            Glide.with(this)
                .load(avatarUrl)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivAvatar)
        }
    }

    private fun selectAvatar() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .setMinSelectNum(1)
            .setImageSpanCount(4)
            .isDisplayCamera(true)
            .isPreviewImage(true)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    if (!result.isNullOrEmpty()) {
                        val media = result[0]
                        val path = if (media.availablePath.isNullOrEmpty()) {
                            media.path
                        } else {
                            media.availablePath
                        }
                        // 保存头像路径（本地路径或上传后的URL）
                        updateAvatar(path)
                    }
                }

                override fun onCancel() {
                    // 用户取消
                }
            })
    }

    private fun updateAvatar(path: String) {
        // 先显示本地图片
        Glide.with(this)
            .load(path)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(ivAvatar)

        // 保存到本地存储
        UserMMKV.avatar = path

        // TODO: 这里可以调用API上传头像到服务器
        // uploadAvatarToServer(path)

        Toast.makeText(requireContext(), "头像已更新", Toast.LENGTH_SHORT).show()
    }

    private fun setupClickListeners() {
        ivAvatar.setOnClickListener { selectAvatar() }
        userInfoSection.setOnClickListener { /* 跳转个人资料 */ }
        rlMyGarage.setOnClickListener { /* 我的车库 */ }
        rlFlowRecharge.setOnClickListener { /* 流量充值 */ }
        rlStolenReport.setOnClickListener { /* 被盗上报 */ }
        rlAfterSales.setOnClickListener { /* 售后服务 */ }
        rlBooking.setOnClickListener { /* 预约服务 */ }
        rlStore.setOnClickListener { /* 门店查询 */ }
        rlCareService.setOnClickListener { /* 关怀服务 */ }
        rlFixOnline.setOnClickListener { /* 在线报修 */ }
        rlOneClickRescue.setOnClickListener { /* 一键救援 */ }
        rlMessageSettings.setOnClickListener { /* 消息设置 */ }
        rlBindWechat.setOnClickListener { /* 绑定微信 */ }
        rlChangePassword.setOnClickListener { /* 修改密码 */ }
        rlLogout.setOnClickListener { logout() }
        rlWiki.setOnClickListener { /* 使用百科 */ }
        rlFaq.setOnClickListener { /* 常见问题 */ }
        rlShare.setOnClickListener { /* 分享应用 */ }
        rlCarUsage.setOnClickListener { /* 用车报告 */ }
    }

    private fun logout() {
        AuthMMKV.clear()
        UserMMKV.clear()
        viewLifecycleOwner.lifecycleScope.launch {
            EventBus.post(LogoutEvent())
            Toast.makeText(requireContext(), "已退出登录", Toast.LENGTH_SHORT).show()
        }
    }
}
