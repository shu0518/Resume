# 🧙‍♂️ AI 教授分身：基於 RAG 的課程智慧助教

這是一個結合 RAG 技術的 AI 助教，能精準檢索課程內容，並模仿教授的說話風格來回答學生問題。

## ✨ 核心特色

* **RAG 課程問答**：結合向量資料庫，依據課程逐字稿與簡報回答，降低 AI 幻覺。
* **教授人格重現**：還原教授的碎念感、口頭禪與關心學生的語氣。
* **強大模型驅動**：使用 **Llama 3.3 (Groq)** 進行推論，搭配 **Google EmbeddingGemma** 處理文本向量。
* **魔法風格 UI**：基於 Gradio 打造的沉浸式魔法主題介面。

## 🚀 快速開始

### 1. 建立知識庫
開啟 `Build_vector_database.ipynb`：
1.  填入存放課程教材 (PDF/TXT) 的 Google Drive 資料夾連結。
2.  執行程式，自動下載教材並建立向量資料庫。
3.  下載產生的 `faiss_db.zip`。

### 2. 啟動 AI 助教
開啟 `GenAI_Final_Project.ipynb`：
1.  設定 Secrets：`HuggingFace` (下載模型用) 與 `Groq` (LLM 用)。
2.  修改 `GDRIVE_FILE_URL` 為您的 `faiss_db.zip` 下載連結。
3.  執行程式，點擊 Gradio 連結即可開始對話。

## 🛠️ 技術使用
* **框架**: Python, LangChain, FAISS
* **模型**: Llama 3.3-70b (via Groq), embeddinggemma-300m
* **介面**: Gradio
