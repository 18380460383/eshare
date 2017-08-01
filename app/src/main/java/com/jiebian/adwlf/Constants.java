package com.jiebian.adwlf;

/**
 * @author wu
 * @version 1.0
 */
public class Constants {
    //APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx698e4bb32e5fa678";
    public static final String SECRET = "e6e3a3742d32a18f511583b122726a5f";
    //public static final String APP_ID = "wx2d5039f859b5a469";
    //public static final String SECRET = "5b36a017aa903def68fbf5a3d8f99325";
    public static final String DES_KEY = "ooCZc5DIoVpUrsJ7JKiJFKb5OZmLw2Ob";
//    /**
//     * 客户端服务器
//     */
    // public static final String SERVER_URL = "http://203.195.238.137/api";

    /*获取服务器各种各种链接的接口*/
    public static final String SERVER_API_CONFIG = "http://api.jiebiannews.com/config.php";

    /**
     * 客户端测1ua试服务器
     */
    //public static final String SERVER_URL = "http://192.168.0.184/api";
    public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 客户端PHP测试(黄)
     */
    //public static final String PHP_SERVER_Z = "http://ipx.api.jiebiannews.com/api.php/Cmd/";
    /**
     * 客户端PHP正式（黄）
     */
    public static final String PHP_SERVER_Z = "http://api.jiebiannews.com/api.php/Cmd/";
    /**
     * 服务器一根路径（赵洪非）
     */
    public static final String URL_SERVER_ONE = "http://api.jiebiannews.com/api.php/App/";
    /**
     * 服务器一根路径（赵洪测试）
     */
    //public static final String URL_SERVER_ONE = "http://ipx.api.jiebiannews.com/api.php/App/";
    /**
     * 增加点击次数
     */
    public static final String URL_SERVER_UP_LOAD_HITS = URL_SERVER_ONE + "UploadHits";
    /**
     * 获取全部省市区信息
     */
    public static final String URL_SERVER_STATE_CITY_AREA_ALL = URL_SERVER_ONE + "StateCityArea_all";
    /**
     * 获取推广费用分配
     */
    public static final String URL_SERVER_COST_DIST = URL_SERVER_ONE + "CostDist";
    /**
     * 图片上传
     */
    public static final String URL_SERVER_UPLOAD_PIC = URL_SERVER_ONE + "UploadPic";
    /**
     * 获取行业信息
     */
    public static final String URL_SERVER_INDUSTRY = URL_SERVER_ONE + "Industry";
    /**
     * 获取兴趣信息
     */
    public static final String URL_SERVER_INTEREST = URL_SERVER_ONE + "Interest";
    /**
     * 添加或编辑推广活动
     */
    public static final String URL_SERVER_PROJECT_UPDATE = URL_SERVER_ONE + "ProjectUpdate";
    /**
     * 推广的状态修改
     */
    public static final String URL_SERVER_UPLOAD_STAT = URL_SERVER_ONE + "UploadStat";
    /**
     * 用户推广信息
     */
    public static final String URL_SERVER_PROJECT_UIDHOME = URL_SERVER_ONE + "ProjectUidHome";
    /**
     * 获取单条推广记录的信息
     */
    public static final String URL_SERVER_PROJECT_SHOW = URL_SERVER_ONE + "ProjectShow";
    /**
     * 获取推广记录
     */
    public static final String URL_SERVER_PROJECT_LIST = URL_SERVER_ONE + "ProjectList";
    /**
     * 绑定手机号
     */
    public static final String URL_SERVER_BINDING_MOBILE = URL_SERVER_ONE + "UserPhone";

    /**
     * 判断手机号码
     */
    public static final String URL_USER_IS_PHONE = URL_SERVER_ONE + "UserIsPhone";

    /**
     * 获取消息列表接口
     */
    public static final String URL_GET_MESSAGES = URL_SERVER_ONE + "MessageList";
    public static final String URL_PUT_JPUSID = URL_SERVER_ONE + "UserJpush";
    public static final String URL_GET_MSGNUM = URL_SERVER_ONE + "MessageReadNum";
    /**
     * 获取详细的消息信息
     */
    public static final String URL_GET_DETAIL_MSG = URL_SERVER_ONE + "MessageShow";
    /**
     * 获取用户余额
     */
    public static final String URL_GET_USERBALANCE = PHP_SERVER_Z + "getUserBalance";

    public static final String URL_GET_RATING = PHP_SERVER_Z + "getRatingList";

    /**
     * 获取公众OpenId
     */
    public static final String URL_ATTENTION = PHP_SERVER_Z + "checkGuanzhu";
    /**
     * 开启应用时上传地址等信息的接口
     */
    public static final String URL_SET_LOCATING = PHP_SERVER_Z + "setUserLocation";
    /**
     * 提现
     */
    public static final String URL_GET_MONEY = PHP_SERVER_Z + "withdrawBalance";
    /**
     * 获取推广的详情
     */
    public static final String URL_GET_C_D = PHP_SERVER_Z + "getProjectDetail";
    /**
     * 获取用户的信息，参数：uid, token
     */
    public static final String URL_GET_INFO_DETAIL = PHP_SERVER_Z + "getUserInfo";
    /**
     * 获取指定的信息列表
     */
    public static final String URL_GET_PROJECT = PHP_SERVER_Z + "getProjectList";
    /**
     * 获取转发记录
     */
    public static final String URL_GET_RELAYLIST = PHP_SERVER_Z + "getRelayList";
    /**
     * 转发成功后，调用
     */
    public static final String URL_RELAY_SUCCESS = PHP_SERVER_Z + "relaySuccess";
    /**
     * 获取二维码
     */
    public static final String URL_GET_QRCODE = PHP_SERVER_Z + "getShareQrcode";
    /**
     * 获取个人记录的收入记录
     */
    public static final String URL_GET_INCOME = URL_SERVER_ONE + "UserIncome";
    /**
     * 获取设置里面的账号信息
     */
    public static final String URL_GET_SETINFO = URL_SERVER_ONE + "UserInfo";
    /**
     * 设置接收推送
     */
    public static final String URL_SET_PUSHINFO = URL_SERVER_ONE + "UserUpdatejpush";
    /**
     * 创建推广链接
     */
    public static final String URL_CREATE_MODE = URL_SERVER_ONE + "ProjectUrlContent";

    /**
     * 获取提现记录
     */
    public static final String URL_GET_WITHDRAW_DEPOSIT = PHP_SERVER_Z + "getWithdrawList";
    /**
     * 上传图片路径
     */
    public static final String URL_UP_IMAGE_URL = PHP_SERVER_Z + "uploadScreenshot";
    /**
     * 获取省市区
     */
    public static final String URL_GET_ADDRESS = PHP_SERVER_Z + "getLocationList";
    /**
     * 获取兴趣
     */
    public static final String URL_GET_INTEREST = PHP_SERVER_Z + "getInterestList";
    /**
     * 获取职业
     */
    public static final String URL_GET_PROFESSION = PHP_SERVER_Z + "getProfessionList";
    /**
     * 获取行业
     */
    public static final String URL_GET_INDUSTRY = PHP_SERVER_Z + "getIndustryList";
    /**
     * 用户登录
     */
    public static final String URL_POST_LOGIN = PHP_SERVER_Z + "login";
    /**
     * 用户注册
     */
    public static final String URL_POST_REGISTER = PHP_SERVER_Z + "register";
    /**
     * 获取学校
     */
    public static final String URL_GET_SCHOOL = PHP_SERVER_Z + "getSchoolList";
    /**
     * 修改个人信息
     */
    public static final String URL_POST_CHANGEUSERINFO = PHP_SERVER_Z + "changeUserInfo";
    /**
     * 互动页广告栏
     */
    public static final String URL_GET_AD = PHP_SERVER_Z + "getHomePageList";
    /**
     * 绑定微信
     */
    public static final String URL_POST_BINDWITHDRAW = PHP_SERVER_Z + "bindingWithdraw";
    /**
     * 获取七牛token
     */
    public static final String URL_POST_QINIUTOKEN = PHP_SERVER_Z + "getQiniuToken";
    /**
     * 获取分享好友列表
     */
    public static final String URL_GET_SHAREFRINDE = URL_SERVER_ONE + "UserShare";
   /* *//**
     * 企业端找回密码
     *//*
    public static final String URL_GET_FINDPASSWORD = URL_SERVER_TOW + "findPassword";*/
    /**
     * 获取企业二维码信息
     */
    public static final String URL_GET_E_TDC = URL_SERVER_ONE + "getCompanyQrcode";
    /**
     * 用户手机注册
     */
    public static final String URL_GET_USER_ADD = URL_SERVER_ONE + "UserAdd";
    /**
     * 用户手机登陆
     */
    public static final String URL_GET_USER_LOGIN = URL_SERVER_ONE + "UserLogin";
    /**
     * 个人端用户修改密码
     */
    public static final String URL_GET_USER_PWD = URL_SERVER_ONE + "UserPwdNew";
    /**
     * 手机号注册用户绑定微信
     */
    public static final String URL_GET_USER_W = URL_SERVER_ONE + "UserWeixin";
    /**
     * 获取分享的展示数据
     */
    public static final String URL_GET_USER_SHARE = PHP_SERVER_Z + "getShareQrcodeNew";
    /**
     * 企业获取分享的展示数据
     */
    public static final String URL_GET_EN_SHARE = PHP_SERVER_Z + "getShareQrcodeNetNews";
    /**
     * 上传企业logo
     */
    public static final String URL_UPLOAD_ElOGO = URL_SERVER_ONE + "UploadLogo";
    /**
     * 用户签到记录
     */
    public static final String URL_GET_SIGN = URL_SERVER_ONE + "SigninInfos";
    /**
     * 设置用户签到
     */
    public static final String URL_SET_SIGN = URL_SERVER_ONE + "UserSignin";

    public static final String URL_ACTIVITY_LIST = URL_SERVER_ONE + "Activity";
    /**
     * 获取启动页广告
     */
    public static final String URL_GET_LOGO_AD = URL_SERVER_ONE + "UserAdvert";
    /**
     * 上传首页广告用户查看信息
     */
    public static final String URL_POST_ADLOOK = URL_SERVER_ONE + "UserLookAdvert";
    /**
     * 获取红包信息
     */
    public static final String URL_GET_REDPACKET = URL_SERVER_ONE + "UserGetRedPackets";
    /**
     * 上传活动点击数
     */
    public static final String URL_POST_ACTHIT = URL_SERVER_ONE + "ActivityHits";
    /**
     * 获取商品列表
     */
    public static final String URL_GET_GOODS = URL_SERVER_ONE + "Goods";
    /**
     * 获取商品详细信息
     */
    public static final String URL_GET_GOODSDETLIS = URL_SERVER_ONE + "GoodsShow";
    /**
     * 积分兑换生成订单
     */
    public static final String URL_POST_USERADDORDER = URL_SERVER_ONE + "UserAddOrder";
    /**
     * 获取用户收货地址
     */
    public static final String URL_GET_GOODSADDRESS = URL_SERVER_ONE + "GetUserAddress";
    /**
     * 编辑收货地址
     */
    public static final String URL_POST_ADDSHOPADDRESS = URL_SERVER_ONE + "UserAddress";

    /**
     * 获取兑换记录
     */
    public static final String URL_GET_EXCHANGE = URL_SERVER_ONE + "UserOrderList";
    /**
     * 兑换详情
     */
    public static final String URL_EXCHANGE_SHOW = URL_SERVER_ONE + "UserOrderShow";
    /**
     * 积分兑换订单支付
     */
    public static final String URL_POST_PLAYMENT = URL_SERVER_ONE + "UserOrderPay";

    public static final String URL_ORDER_STATE = URL_SERVER_ONE + "UserOrderState";
    /**
     * 咨询分类信息
     */
    public static final String URL_GET_NEWS_TYPE = URL_SERVER_ONE + "NewsType";
    /**
     * 获取某一个类型的咨询
     */
    public static final String URL_GET_NEWS_LIST = URL_SERVER_ONE + "NewsList";
    /**
     * 获取特殊人群列表
     * 企业家，媒体人
     */
    public static final String URL_GET_E_USERROLELIST = URL_SERVER_ONE + "UserRoleList";
    /**
     * 跟新企业展示信息
     */
    public static final String URL_UPDATE_COMPANY = URL_SERVER_ONE + "UpdateCompany";
    /**
     * 获取用户的认证信息
     */
    public static final String URL_GET_ROLELIST = URL_SERVER_ONE + "RoleList";
    /**
     * 更新媒体认证信息
     */
    public static final String URL_UPDATE_ROLE = URL_SERVER_ONE + "UpdateRole";

    /**
     * 获取指定转发推广列表
     */
    public static final String URL_PUSH_ROLES = URL_SERVER_ONE + "ProjectPushRoles";

    /**
     * 认证信息详情
     */
    public static final String URL_ROLES_DETAIL = URL_SERVER_ONE + "Role";

    /**
     * 获取企业展示信息
     */
    public static final String URL_GET_COMPANY = URL_SERVER_ONE + "GetCompany";

    /**
     * 获取用户关注的企业列表
     */
    public static final String URL_LIST_ENTER = URL_SERVER_ONE + "UserFollowCompany";
    /**
     * 获取用户相关互动 资讯数据（1.1）
     */
    public static final String URL_Get_PERSONL_USER_NEWS = URL_SERVER_ONE + "findPagePersonalUserNews";
    /**
     * 获取用户资讯详情（1.1）
     */
    public static final String URL_Get_PERSONL_USER_NEWS_DETILS = URL_SERVER_ONE + "findOneNews";
    /**
     * 获取用户资讯评论（1.1）
     */
    public static final String URL_Get_PERSONL_USER_NEWS_COMMENT = URL_SERVER_ONE + "findPageNewsComment";    /**
     * 获取用户资讯精华评论（1.1）
     */
    public static final String URL_Get_PERSONL_USER_NEWS_COMMENT_BEST = URL_SERVER_ONE + "findPageNewsCommentBest";
    /**
     * 增加评论（1.1）
     */
    public static final String URL_POST_ADD_NEWS_COMMENT = URL_SERVER_ONE + "addNewsOpinions";
    /**
     * 收藏（1.1）
     */
    public static final String URL_GET_Collect = URL_SERVER_ONE + "findPageCollectByUid";
    /**
     * 批量点赞（1.1）
     */
    public static final String URL_POST_ZAN = URL_SERVER_ONE + "addNewsCommentLike";
    /**
     * 收藏资讯（1.1）
     */
    public static final String URL_POST_COLLECT = URL_SERVER_ONE + "addNewsCollect";
    /**
     * 资讯转发成功（1.1）
     */
    public static final String URL_POST_SHARENEWS = URL_SERVER_ONE + "addNewsRelay";
    /**
     * 资讯转评论发成功（1.1）
     */
    public static final String URL_POST_SHARE_COMMENT = URL_SERVER_ONE + "addNewsCommentRelay";
    /**
     * 资讯举报（1.1）
     */
    public static final String URL_POST_REPORT = URL_SERVER_ONE + "addNewsCommentReport";
    /**
     * 发送验证码
     */
    public static final String URL_POST_NOTE = URL_SERVER_ONE + "sendmobile";
    /**
     * 判断验证码
     */
    public static final String URL_POST_NOTE_JUDGE_CODE = URL_SERVER_ONE + "codeMobileNoSign";

    public static final String URL_UP = URL_SERVER_ONE + "GETbate";
    /**
     * 进入企业登录时  判断是否绑定个人端
     */
    public static final String URL_P_GET_FOR_E = URL_SERVER_ONE + "getBundlingnetnews";
    /**
     * 进入个人端登录时判断当前登录的企业号有没有绑定个人
     */
    public static final String URL_E_GET_FOR_P = URL_SERVER_ONE + "getBundlingjiebian";
    /**
     * 用企业张海登录个人端进行绑定
     */
    public static final String URL_BINDING_E_FOR_P = URL_SERVER_ONE + "netnewsBundingJiebian";
    /**
     * 将需要绑定的企业信息发个个人端后台
     */
    public static final String URL_E_TO_P = URL_SERVER_ONE + "addBundlingnetnews";

    public static final String URL_POST_EN_RECHARGE = URL_SERVER_ONE + "addOrderNetnews";
    /**
    * 邮箱发送验证码
    * */
    public static final String URL_POST_EMAILCODE = URL_SERVER_ONE + "sendUserEmailCode";

    /**
     * 绑定修改邮箱
     */
    public static final String URL_SAVE_EMAIL = URL_SERVER_ONE + "saveUserEmail";

    /**
     * 首页资讯类型
     */
    public static final String URL_GET_NEWSTYPE = URL_SERVER_ONE + "NewsType";


    public static final String BROADCAST_RED = "com.brocast.red";

    public static final String HONGDIAN = "com.brocast.redspot";

    public static final String FRAGMENT_MONEY = "com.brocast.money";
    public static final String SHOP_PLAY_OK = "com.brocast.shop.play";
    /**
     * 微信授权广播
     */
    public static final String WEIXIN_ACCREDIT = "com.brocast.weixin.accredit";
    /**
     * 微信授权用户信息KEY
     */
    public static final String WEIXIN_ACCREDIT_KEY = "info";
    /**
     * 微信分享广播
     */
    public static final String WEIXIN_SHARE = "com.brocast.weixin.share";
    /**
     * 微信分享相应KEY
     */
    public static final String WEIXIN_SHARE_KEY = "weixin_share";
    /**
     * 微信分享成功code
     */
    public static final int WEIXIN_SHARE_VALUE_SUCCEED = 1;
    /**
     * 微信分享失败code
     */
    public static final int WEIXIN_SHARE_VALUE_FAILURE = 2;
    /**
     * 分享类型为微信
     */
    public static final String SHARE_TYPE_WEIXIN = "1";
    /**
     * 应用文件夹
     */
    public static final String ESHARE_ROOT_PATH = "eshare";
    /**
     * 企业端缓存图片文件夹
     */
    public static final String E_PATH_IMG = "zooms";
    /**
     * 个人端资讯下载图片文件夹
     */
    public static final String C_PATH_DIMG = "download";
    /**
     * ImageLoader图片缓存文件夹
     */
    public static final String LOADER_PATH_IMG = "cache";

    /**
     * 本地数据库
     * 2016.4.26
     */
    public static final String DB_NAME = "eshare_db";
    /**
     * 本地数据库版本
     */
    public static final int DB_VERSION = 3;
    /**
     * 1.1版本资讯表
     */
    public static final String DB_USER_NEWS = "eshare_news";
    /**
     * 1.1版本资讯评论表
     */
    public static final String DB_USER_NEWS_COMMENT = "eshare_news_comment";
    /**
     * 1.1版本PDF下载管理
     */
    public static final String DB_PDF = "eshare_pdf";

}
