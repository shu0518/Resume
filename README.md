# 📂  Projects Portfolio

## 1. 🤖 Gen_AI_Final_Project
**基於 RAG 的生成式 AI 課程智慧助教**
本專案實作一個能模仿教授語氣、依據課程教材回答問題的 AI 助教系統。

* **核心技術**：結合 **RAG** ，使用 **Llama 3.3** LLM 與 **Google EmbeddingGemma** 處理向量檢索。
* **關鍵功能**：
    * **知識庫建立**：`Build_vector_database.ipynb` 負責將 PDF/TXT 教材轉換為向量資料庫。
    * **互動介面**：`GenAI_Final_Project.ipynb` 使用 **Gradio** 打造 Chatbot 介面，並內建教授人格設定。
* **資料來源**：`dataset/` 資料夾內含完整的課程逐字稿（如第 01 講至第 15 講）作為知識來源。

---

## 2. ⛏️ data-mining-projects
**資料探勘實作合集**
本資料夾收錄資料探勘四大核心領域的實作專案，重點在於演算法的比較與應用。

* **🌲 決策樹分類 (`01-decision-tree`)**
    * 針對 Adult Dataset 進行收入預測，實作並比較 **ID3**, **C4.5**, **CART** 與 **C5.0 (with Boosting)** 演算法。
* **📈 迴歸預測 (`02-regression`)**
    * 使用 Boston Housing Dataset 預測房價，比較 **XGBoost**, **Random Forest**, **SVR** 與 **KNN** 的效能，並採用 K-Fold 交叉驗證。
* **🍌 分群分析 (`03-clustering`)**
    * 探討非凸集資料分群，驗證 **DBSCAN** 在處理複雜形狀（如香蕉形狀數據）時優於 **K-Means** 與階層式分群的表現。
* **🛒 關聯規則 (`04-association-rule`)**
    * 進行購物籃分析，實作 **Apriori** 與 **FP-Growth** 演算法來挖掘商品關聯，並建立互動式推薦系統。

---

## 3. 📄 Resume & Other Projects (履歷與其他作品)

本區塊展示個人履歷、網頁開發作品以及畢業專題的完整架構（包含 Web、App 與 AI 模型訓練）。

### 💼 個人作品集
* **📝 Resume**：履歷用文件。
* **📊 數據分析**：`python 數據分析作品集` (Python Data Analysis Portfolio)。
* **🌐 ASP .NET MVC**：`NETFinal` (ASP .NET MVC 作品集)。

### 🎓 畢業專題實作
整合 Web、App 與 AI 物件分割技術的專題展示。

#### **專案架構**
* **Demo 圖片**：`Project_Photo`
* **Web 端**：`tiaotiaokuangkuang`
* **App 端**：`ttkk_full_ver`

#### **🧠 AI 模型：物件分割 (Window Detection)**
* **模型包名稱**：`window_detection`
* **使用模型**：**YOLOv11m-seg**
* **關鍵程式碼路徑**：
    * 前端佈局：`front_end1104/app/src/main/res/layout`
    * 後端邏輯：`front_end1104/app/src/main/java/com/example/a0731`

#### **🏋️‍♂️ 模型訓練流程**
專題製作主要採用 **Ultralytics Hub** 上現成的基礎模型進行訓練。若需進行本機訓練，簡易流程如下：

1.  **環境準備**
    * 安裝 Python 3.8 或更新版本。
    * 配置 CUDA Toolkit 和 cuDNN。
    * 安裝與 GPU 驅動相容的 PyTorch。
2.  **數據集準備**
    * 使用 **labelme** 標註影像（越詳細標出物件輪廓，訓練效果越好）。
    * 將數據按照訓練、驗證和測試集分配（建議比例：80% / 10% / 10%）。
3.  **模型加載與開始訓練**
    * 選擇 YOLO 模型。
    * 調整參數並開始訓練。
