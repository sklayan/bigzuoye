import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    try {
      config.headers = config.headers || {};
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      // lightweight debug: log outgoing request details (remove or guard in production)
      console.debug('API request ->', {
        method: config.method,
        url: config.url,
        params: config.params,
        data: config.data,
        tokenSnip: token ? `${token?.toString().slice(0, 10)}...` : null,
      });
    } catch (e) {}
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
