/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo;


import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chatuidemo.db.DbOpenHelper;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.zeone.framework.app.ApplicationBean;
import com.zeone.framework.image.ImageLoadManager;
import com.zeone.framework.image.ImageCache.ImageCacheParams;

public class DemoApplication extends ApplicationBean {
	public static Context applicationContext;
	private static DemoApplication huanxinInstance;
	// login user name
	public final String PREF_USERNAME = "username";
	private String chatUserName = null;
	// login chatPassword
	private static final String PREF_PWD = "pwd";
	private String chatPassword = null;
	private Map<String, User> contactList;
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";

	private static DemoApplication app = null;
    //登陆人经度
	public static double currentLongitude;
	//登陆人纬度
	public static double currentLatitude;
	//当前网络类型
	public static int currentNetworkType = 1;
	
//	@Override
//	public void init() {
//		app = this;
//		ImageCacheParams  parms = new ImageCacheParams(this.getApplicationContext(), "images");
//		parms.setMemCacheSizePercent(0.25f);
//		ImageLoadManager.instance().addImageCache(parms);
//	}

	@Override
	public void onCreate() {
		super.onCreate();
		
        applicationContext = this;
        huanxinInstance = this;
        // 初始化环信SDK,一定要先调用init()
//        EMChat.getInstance().init(applicationContext);
//        EMChat.getInstance().setDebugMode(false);
//        Log.d("EMChat Demo", "initialize EMChat SDK");
//        // debugmode设为true后，就能看到sdk打印的log了
//		//注册一个语言电话的广播接收者
//		IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance().getIncomingVoiceCallBroadcastAction());
//		registerReceiver(new VoiceCallReceiver(), callFilter);		
	}

	public static DemoApplication getInstance() {
		return huanxinInstance;
	}

	/**
	 * 获取内存中好友user list
	 *
	 * @return
	 */
	public Map<String, User> getContactList() {
		if (getChatUserName() != null && contactList == null) {
			UserDao dao = new UserDao(applicationContext);
			// 获取本地好友user list到内存,方便以后获取好友list
			contactList = dao.getContactList();
		}
		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 *
	 * @param contactList
	 */
	public void setContactList(Map<String, User> contactList) {
		this.contactList = contactList;
	}

	public void setStrangerList(Map<String, User> List) {

	}

	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getChatUserName() {
		if (chatUserName == null) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
			chatUserName = preferences.getString(PREF_USERNAME, null);
		}
		return chatUserName;
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getChatPassword() {
		if (chatPassword == null) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
			chatPassword = preferences.getString(PREF_PWD, null);
		}
		return chatPassword;
	}

	/**
	 * 设置用户名
	 *
	 * @param user
	 */
	public void setChatUserName(String username) {
		if (username != null) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
			SharedPreferences.Editor editor = preferences.edit();
			if (editor.putString(PREF_USERNAME, username).commit()) {
				chatUserName = username;
			}
		}
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setChatPassword(String pwd) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_PWD, pwd).commit()) {
			chatPassword = pwd;
		}
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout() {
		// 先调用sdk logout，在清理app中自己的数据
		try {
			EMChatManager.getInstance().logout();
			DbOpenHelper.getInstance(applicationContext).closeDB();
			// reset chatPassword to null
			setChatPassword(null);
			setContactList(null);
		} catch (Exception e) {
			// FTODO: handle exception
		}
	
	}

}
