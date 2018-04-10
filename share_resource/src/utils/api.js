import axios from 'axios'

let prefixUrl = '/api';

export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${prefixUrl}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&' ;
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}
//上传文件请求
export const uploadRequest = (url, form, config) => {
    return axios.post(`${prefixUrl}${url}`,form,config);
}

export const getRequest = (url,params) => {
    return axios({
      method: 'get',
      params: params,
      transformRequest: [function (data) {
        let ret = ''
        for (let it in data) {
          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      url: `${prefixUrl}${url}`
    });
}

export const getRequest2 = (url,params) => {
    return axios({
      method: 'get',
      params: params,
      responseType: 'blob',
      transformRequest: [function (data) {
        let ret = ''
        for (let it in data) {
          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      url: `${prefixUrl}${url}`
    });
  }
  
  export const isSuccess = (code) => {
      if (code == 0) {
          return true;
      } else {
          return false;
      }
  }