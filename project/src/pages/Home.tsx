import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { articleApi, Article } from '../api/article';
import {
  Search,
  LogOut,
  PenSquare,
  MessageCircle,
  Heart,
  Edit,
  Trash2,
} from 'lucide-react';
import CreateArticleModal from '../components/CreateArticleModal';

const Home = () => {
  const [articles, setArticles] = useState<Article[]>([]);
  const [loading, setLoading] = useState(false);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingArticle, setEditingArticle] = useState<Article | null>(null);
  const [viewMode, setViewMode] = useState<'public' | 'personal'>('public');
  const { logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
  loadArticles(searchKeyword);
}, [viewMode]);


  const loadArticles = async (keyword?: string) => {
  setLoading(true);
  try {
    let response: any;

    if (viewMode === 'personal') {
      response = await articleApi.myList();
    } else {
      response = await articleApi.list(keyword);
    }

    const items = response?.data || [];
    
    setArticles(items);
  } catch (err) {
    console.error('Failed to load articles', err);
  } finally {
    setLoading(false);
  }
};

    const handleToggleLike = async (article: Article) => {
    try {
      const res: any = await articleApi.toggleLike(article.id!);

      if (res.code === 200) {
        const { liked, likeCount } = res.data;

        setArticles((prev) =>
          prev.map((a) =>
            a.id === article.id
              ? { ...a, liked, likeCount }
              : a
          )
        );
      }
    } catch (err: any) {
      alert(err.response?.data?.message || '点赞失败');
    }
  };

  const handleSearch = () => {
    loadArticles(searchKeyword);
  };

  const handleCreateArticle = async (article: Article) => {
    console.debug('Creating article ->', article);
    try {
      if (editingArticle) {
        const response: any = await articleApi.update(article);
        if (response.code === 200) {
          alert('更新成功');
          loadArticles(searchKeyword);
        }
      } else {
        const response: any = await articleApi.create(article);
        console.debug('Create response ->', response);
        if (response.code === 200) {
          alert('发布成功');
          loadArticles(searchKeyword);
        }
      }
      setIsModalOpen(false);
      setEditingArticle(null);
    } catch (err: any) {
      alert(err.response?.data?.message || '操作失败');
    }
  };

  const handleDeleteArticle = async (id: number) => {
    if (!confirm('确定要删除这篇博客吗？')) return;

    try {
      const response: any = await articleApi.delete(id);
      if (response.code === 200) {
        alert('删除成功');
        loadArticles(searchKeyword);
      }
    } catch (err: any) {
      alert(err.response?.data?.message || '删除失败');
    }
  };

  const handleEditArticle = (article: Article) => {
    setEditingArticle(article);
    setIsModalOpen(true);
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const getCurrentUserId = () => {
    const token = localStorage.getItem('token');
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.userId || payload.sub;
    } catch {
      return null;
    }
  };

  const currentUserId = getCurrentUserId();
  const filteredArticles =
    viewMode === 'personal'
      ? articles.filter((a) => String(a.userId) === String(currentUserId))
      : articles;

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between">
          <h1 className="text-3xl font-bold text-gray-800">个人博客系统</h1>
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <input
                type="text"
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                placeholder="搜索博客..."
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
              <button
                onClick={handleSearch}
                className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition flex items-center gap-2"
              >
                <Search className="w-4 h-4" />
                搜索
              </button>
            </div>
            <button
              onClick={handleLogout}
              className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition flex items-center gap-2"
            >
              <LogOut className="w-4 h-4" />
              退出登录
            </button>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-6 flex gap-6">
        <div className="w-48 flex-shrink-0">
          <div className="bg-white rounded-lg shadow-sm p-4 space-y-2 sticky top-6">
            <button
              onClick={() => setViewMode('public')}
              className={`w-full px-4 py-3 rounded-lg font-medium transition ${
                viewMode === 'public'
                  ? 'bg-blue-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              公开博客
            </button>
            <button
              onClick={() => setViewMode('personal')}
              className={`w-full px-4 py-3 rounded-lg font-medium transition ${
                viewMode === 'personal'
                  ? 'bg-blue-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              个人博客
            </button>
          </div>

          <button
            onClick={() => {
              setEditingArticle(null);
              setIsModalOpen(true);
            }}
            className="w-full mt-4 px-4 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition flex items-center justify-center gap-2 font-medium"
          >
            <PenSquare className="w-5 h-5" />
            发送博客
          </button>
        </div>

        <div className="flex-1">
          {loading ? (
            <div className="text-center py-12 text-gray-500">加载中...</div>
          ) : filteredArticles.length === 0 ? (
            <div className="text-center py-12 text-gray-500">
              暂无博客，快来发布第一篇吧
            </div>
          ) : (
            <div className="space-y-4">
              {filteredArticles.map((article) => (
                <div
                  key={article.id}
                  className="bg-white rounded-lg shadow-sm p-6 hover:shadow-md transition"
                >
                  <div className="flex items-start justify-between mb-3">
                    <div>
                      <h2 className="text-xl font-bold text-gray-800 mb-2">
                        {article.title}
                      </h2>
                      <p className="text-sm text-gray-500">
                        {article.username || '匿名用户'} •{' '}
                        {new Date(article.createdAt || '').toLocaleString()}
                      </p>
                    </div>
                  </div>

                  <p className="text-gray-700 mb-4 whitespace-pre-wrap">
                    {article.content}
                  </p>

                  <div className="flex items-center gap-4 pt-4 border-t">
                    <button
                      onClick={() => navigate(`/comments/${article.id}`)}
                      className="flex items-center gap-2 text-gray-600 hover:text-blue-600 transition"
                    >
                      <MessageCircle className="w-5 h-5" />
                      <span>评论 [{article.commentCount || 0}]</span>
                    </button>

                    <button
                      onClick={() => handleToggleLike(article)}
                      className="flex items-center gap-2 text-gray-600 hover:text-pink-600 transition"
                    >
                    <Heart
                      className={`w-5 h-5 ${
                      article.liked ? 'fill-pink-500 text-pink-500' : ''
                      }`}
                    />
                    <span>点赞 [{article.likeCount || 0}]</span>
                    </button> 
                        
                    {viewMode === 'personal' &&
                      String(article.userId) === String(currentUserId) && (
                        <>
                          <button
                            onClick={() => handleEditArticle(article)}
                            className="flex items-center gap-2 text-gray-600 hover:text-green-600 transition"
                          >
                            <Edit className="w-5 h-5" />
                            <span>编辑</span>
                          </button>

                          <button
                            onClick={() => handleDeleteArticle(article.id!)}
                            className="flex items-center gap-2 text-gray-600 hover:text-red-600 transition"
                          >
                            <Trash2 className="w-5 h-5" />
                            <span>删除</span>
                          </button>
                        </>
                      )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <CreateArticleModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingArticle(null);
        }}
        onSubmit={handleCreateArticle}
        editArticle={editingArticle}
      />
    </div>
  );
};

export default Home;
