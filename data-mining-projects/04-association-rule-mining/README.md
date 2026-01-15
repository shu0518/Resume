# Data Mining Project 4: Association Rule Mining

本專案的目標是進行**購物籃分析 (Market Basket Analysis)**，從大量的交易資料中挖掘商品之間的關聯規則，並據此建立一個簡單的產品推薦系統。

使用模擬的電子商務交易資料，透過 **Apriori / FP-Growth** 演算法找出「購買商品 A 的人，通常也會購買商品 B」的強關聯規則。

## 📂 專案結構

本專案將資料處理與分析流程分為兩個主要部分：

### 1. 資料前處理
- **`Data_Preprocessing.ipynb`**:
  - **資料清洗**: 剔除無效交易（如退貨數量 <= 0）、移除異常客戶 ID (999999999)。
  - **格式統一**: 將交易日期標準化為 datetime 格式。
  - **產出檔案**: 將清洗後的乾淨資料儲存為 `交易資料集_processed.csv`，供後續分析使用。

### 2. 關聯分析與推薦 (🌟 Highlight)
- **`Associative_Analysis .ipynb`**:
  - **交易編碼**: 將流水帳式的交易紀錄轉換為適合演算法運算的 One-hot Encoding (Basket format)。
  - **挖掘規則**: 使用 `mlxtend` 套件挖掘頻繁項目集 (Frequent Itemsets) 與關聯規則 (Association Rules)。
  - **篩選指標**: 設定 **Support (支持度)**、**Confidence (信心度)** 與 **Lift (提升度)** 門檻來過濾強規則。
  - **互動式推薦**: 實作了一個 `interactive_recommendation_system` 函式，輸入商品名稱後，系統會自動推薦相關聯的商品。

### 資料集
- **`交易資料集.csv`**: 原始交易紀錄 (Raw Data)。
- **`交易資料集_processed.csv`**: 經清洗後的資料，包含 `INVOICE_NO`, `ITEM_NO`, `PRODUCT_TYPE` 等關鍵欄位。

## 🚀 專案亮點 

- **完整 ETL 流程**: 從 Raw Data 清洗到特徵轉換的完整實作。
- **商業指標應用**: 使用 **Lift > 1** 來確保規則具有正向相關性（比隨機猜測準確）。
- **推薦系統實作**: 
  > *User Input:* "商品A"  
  > *System Output:* "推薦您購買：商品B (信心度: 80%)"
