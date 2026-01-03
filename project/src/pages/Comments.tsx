import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { commentApi, Comment } from '../api/comment';
import { ArrowLeft, MessageSquare } from 'lucide-react';
import CreateCommentModal from '../components/CreateCommentModal';

const Comments = () => {
  const { articleId } = useParams<{ articleId: string }>();
  const [comments, setComments] = useState<Comment[]>([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (articleId) {
      loadComments();
    }
  }, [articleId]);

  const loadComments = async () => {
    if (!articleId) return;

    setLoading(true);
    try {
      const response: any = await commentApi.listByArticle(Number(articleId));
      if (response.code === 200 && response.data) {
        setComments(response.data);
      }
    } catch (err) {
      console.error('Failed to load comments:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddComment = async (content: string) => {
    if (!articleId) return;

    try {
      const response: any = await commentApi.add({
        articleId: Number(articleId),
        content,
      });
      if (response.code === 200) {
        alert('评论成功');
        loadComments();
        setIsModalOpen(false);
      }
    } catch (err: any) {
      alert(err.response?.data?.message || '评论失败');
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-4xl mx-auto px-4 py-4 flex items-center justify-between">
          <button
            onClick={() => navigate(-1)}
            className="flex items-center gap-2 px-4 py-2 text-gray-700 hover:text-gray-900 transition"
          >
            <ArrowLeft className="w-5 h-5" />
            <span className="font-medium">返回</span>
          </button>
          <h1 className="text-2xl font-bold text-gray-800">评论</h1>
          <div className="w-24"></div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto px-4 py-6">
        {loading ? (
          <div className="text-center py-12 text-gray-500">加载中...</div>
        ) : comments.length === 0 ? (
          <div className="text-center py-12">
            <MessageSquare className="w-16 h-16 text-gray-300 mx-auto mb-4" />
            <p className="text-gray-500">暂无评论，快来发表第一条评论吧</p>
          </div>
        ) : (
          <div className="space-y-4">
            {comments.map((comment) => (
              <div
                key={comment.id}
                className="bg-white rounded-lg shadow-sm p-6 hover:shadow-md transition"
              >
                <div className="flex items-start justify-between mb-3">
                  <div>
                    <p className="font-medium text-gray-800">
                      {comment.username || '匿名用户'}
                    </p>
                    <p className="text-sm text-gray-500">
                      {new Date(comment.createdAt || '').toLocaleString()}
                    </p>
                  </div>
                </div>
                <p className="text-gray-700 whitespace-pre-wrap">
                  {comment.content}
                </p>
              </div>
            ))}
          </div>
        )}

        <button
          onClick={() => setIsModalOpen(true)}
          className="fixed bottom-8 right-8 px-6 py-4 bg-blue-600 text-white rounded-full shadow-lg hover:bg-blue-700 transition flex items-center gap-2 font-medium"
        >
          <MessageSquare className="w-5 h-5" />
          发表评论
        </button>
      </div>

      <CreateCommentModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddComment}
      />
    </div>
  );
};

export default Comments;
