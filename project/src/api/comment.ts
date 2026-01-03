import api from './axios';

export interface Comment {
  id?: number;
  articleId: number;
  content: string;
  userId?: number;
  username?: string;
  createdAt?: string;
}

export const commentApi = {
  add: (data: Comment) => api.post('/comment', data),
  listByArticle: (articleId: number) =>
    api.get(`/comment/article/${articleId}`),
  delete: (id: number) => api.delete(`/comment/${id}`),
};
