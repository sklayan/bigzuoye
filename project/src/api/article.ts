import api from './axios';

export interface Article {
  id?: number;
  title: string;
  content: string;
  userId?: number;
  username?: string;
  likeCount?: number;
  commentCount?: number;
  liked?: boolean;
  createdAt?: string;
}

export const articleApi = {
  // 公开博客
  list: (keyword?: string) =>
    api.get('/article/list', {
      params: keyword ? { keyword } : {},
    }),

  // 我的博客
  myList: () =>
    api.get('/article/my'),

  create: (data: Article) => api.post('/article', data),
  update: (data: Article) => api.put('/article', data),
  delete: (id: number) => api.delete(`/article/${id}`),
  getById: (id: number) => api.get(`/article/${id}`),
  toggleLike: (id: number) => {
  return api.post(`/artlike/${id}/like`);
},

};
