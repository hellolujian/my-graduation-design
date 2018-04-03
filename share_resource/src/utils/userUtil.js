//判断用户是否登录并获取用户信息
export const isUserLogin = () =>{
    let userInfo = JSON.parse(localStorage.getItem('userInfo'));
    //console.log('userinfo:'+userInfo);
    if(userInfo == null)
        return null;
    else 
        return userInfo;
}
//用户登出操作
export const loginOut = () =>{
    localStorage.removeItem('userInfo');
}
//用户登录操作
export const login = (userInfo) =>{
    localStorage.setItem('userInfo',JSON.stringify(userInfo));
}
