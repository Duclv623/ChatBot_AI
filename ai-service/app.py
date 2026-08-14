from flask import Flask, request, jsonify

from utils.vector_db import get_retriever
from utils.rag_chain import get_llm, create_rag_chain

# --- Bước 1: Khởi tạo Retriever (Pinecone hoặc Chroma) ---
retriever = get_retriever()

# --- Bước 2: Khởi tạo LLM ---
llm = get_llm()

# --- Bước 3: Tạo chuỗi RAG hoàn chỉnh ---
rag_chain = create_rag_chain(retriever, llm)

# --- Khởi tạo Flask App ---
app = Flask(__name__)

@app.route('/ask', methods=['POST'])
def ask_question():
    """
    API endpoint để nhận câu hỏi và trả về câu trả lời từ RAG chain.
    """
    try:
        data = request.get_json()
        query = data.get('query')

        if not query:
            return jsonify({"error": "Vui lòng cung cấp 'query' trong body của request."}), 400

        print(f"\nNhận được câu hỏi: {query}")

        # Gọi RAG chain để lấy câu trả lời
        response = data
        response.pop('content', None)
        response.update(answer)

        return jsonify(response)

    except Exception as e:
        print(f"Đã xảy ra lỗi: {e}")
        return jsonify({"error": "Đã có lỗi xảy ra trên server."}), 500

if __name__ == '__main__':
    # Chạy Flask app trên cổng 5000
    app.run(host='0.0.0.0', port=5000, debug=True)
