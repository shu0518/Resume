<div align="center">

# 🧙‍♂️ ProfAI: RAG-based Teaching Assistant

**一個基於 RAG 技術與大型語言模型的 AI 課程助教，精準檢索知識並完美重現教授授課風格。**


[![Python 3.8+](https://img.shields.io/badge/Python-3.8+-blue.svg)](https://www.python.org/)
[![Framework: LangChain](https://img.shields.io/badge/Framework-LangChain-green)](https://python.langchain.com/)
[![UI: Gradio](https://img.shields.io/badge/UI-Gradio-orange)](https://gradio.app/)

</div>

---

## Key Features

*   **精準的 RAG 知識檢索**：結合向量資料庫（FAISS），直接基於上傳的課程簡報（PDF）與逐字稿進行回答，降低 AI 幻覺。
*   **教授人格與語氣重現**：不只是冷冰冰的問答機器！系統針對教授的語氣進行提示工程（Prompt Engineering）微調。
*   **沉浸式魔法風格 UI**：採用 Gradio 打造，具備直覺且主題化的互動聊天介面。
*   **強大的開源模型驅動**：採用 **Llama 3.3-70b (via Groq)**，搭配 **Google embeddinggemma-300m** 進行高維度文本向量化。

## Tech Stack

*   **Core Language**: Python 3
*   **LLM & Inference**: Llama 3.3-70b (Groq API)
*   **Embeddings**: `embeddinggemma-300m` (HuggingFace)
*   **RAG Framework**: LangChain
*   **Vector Database**: FAISS (Facebook AI Similarity Search)
*   **Frontend / UI**: Gradio

## Quick Start

### 1. 取得專案
```bash
git clone https://github.com/shu0518/Resume.git
cd Resume/RAG_AI_Teaching_Assistant
```

### 2. 安裝套件

```bash
pip install -U langchain langchain-community pypdf pymupdf python-docx faiss-cpu unstructured gdown
pip install -U sentence-transformers langchain-text-splitters gradio
```

### 3. 建立向量知識庫 (Vector Database)
1. 開啟或執行 `Build_vector_database.ipynb`。
2. 填入你存放課程教材（PDF/Word/TXT）的 Google Drive 資料夾連結。
3. 執行指令下載教材並切塊（Text Splitter），系統將自動生成 `faiss_db.zip`。

### 4. 啟動 AI 助教
1. 開啟 `GenAI_Final_Project.ipynb`。
2. 將生成的 `faiss_db.zip` 上傳或掛載至環境中（修改 `GDRIVE_FILE_URL`）。
3. 執行所有儲存格，點擊終端機輸出的 **Gradio Public URL** 即可開始與 AI 教授對話！

## Project Structure

```text
RAG_AI_Teaching_Assistant/
├── Build_vector_database.ipynb  # 處理文本載入、切塊與 FAISS 向量庫建置
├── GenAI_Final_Project.ipynb    # RAG 檢索邏輯、LLM 串接與 Gradio UI 啟動
└── README.md                    # 專案說明文件
```
> **Note**: 實際執行時會產生 `faiss_db.zip` 與暫存的資料夾，建議將這些大檔案加入 `.gitignore` 中。

## Environment Variables

專案依賴外部的 API 服務進行推理與模型下載。在執行 `GenAI_Final_Project.ipynb` 前，請確保你已設定以下環境變數（或於 Colab/Jupyter 的 Secrets 中設定）：

| 變數名稱 | 說明 | 取得方式 |
| :--- | :--- | :--- |
| `GROQ_API_KEY` | 用於呼叫 Llama 3.3 模型進行超高速推論。 | [Groq Console](https://console.groq.com/) 申請 |
| `HF_TOKEN` | 用於從 HuggingFace 下載 `embeddinggemma-300m` 模型。 | [HuggingFace](https://huggingface.co/settings/tokens) 申請 |

## API & Integration

雖然本專案主要透過 Gradio 提供網頁介面，但核心的 RAG 檢索與生成邏輯被封裝在以下流程中，方便未來整合至 FastAPI 或其他後端框架：

| 方法 / 模組 | 用途 | 輸入參數 | 輸出格式 |
| :--- | :--- | :--- | :--- |
| `PyMuPDFLoader` / `TextLoader` | 載入教材文件 | `file_path` (PDF/TXT 路徑) | `Document` Objects |
| `FAISS.from_documents` | 建立並儲存向量庫 | `documents`, `embeddings` | `.faiss` & `.pkl` 索引檔 |
| `RetrievalQA` (LangChain) | RAG 檢索與生成 | `query` (學生提問) | `string` (AI 教授的回答) |
