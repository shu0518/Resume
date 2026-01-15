# Data Mining Assignment 3: Clustering Analysis

本專案的目標是實作並比較三種不同類型的非監督式學習分群演算法，並探討它們在不同資料分佈下的表現。

使用兩個具有挑戰性的 2D 資料集來驗證演算法：**Banana Dataset**（非凸集/長條狀）與 **Sizes3 Dataset**（不同密度/大小）。

## 📂 專案結構

本專案包含以下核心程式碼：

### 分群演算法實作
- **`K_means.ipynb`**:
  - 實作 **K-Means Clustering**（劃分式分群）。
  - 觀察其在球狀分佈資料上的優勢，以及在非球狀資料（如 Banana）上的限制。
  
- **`Hierarchical.ipynb`**:
  - 實作 **Hierarchical Clustering**（階層式分群）。
  - 使用 Agglomerative 策略，並產生樹狀圖（Dendrogram）來觀察資料的階層結構。

- **`DBSCAN.ipynb`**:
  - 實作 **DBSCAN**（Density-Based Spatial Clustering of Applications with Noise）。
  - 重點在於調整 `epsilon` 和 `min_samples` 參數，展示其處理任意形狀分群與抗雜訊的能力。

### 測試資料集
- **`banana.csv`**:
  - 特徵：包含兩個互相纏繞的新月形（香蕉狀）聚類。
  - 用途：測試演算法處理**非線性/非凸集 (Non-convex)** 邊界的能力（通常 K-Means 會失敗，DBSCAN 會成功）。
  
- **`sizes3.csv`**:
  - 特徵：包含不同大小與密度的聚類。
  - 用途：測試演算法對於**密度不均**或**群集大小差異大**的適應性。

## 🚀 實驗觀察

透過實驗，我們比較了各演算法在視覺化結果上的差異：

| Algorithm | Method Type | Handling "Banana" Shape | Handling Noise |
|-----------|-------------|-------------------------|----------------|
| **K-Means** | Centroid-based | ❌ Poor (Cuts through shapes) | ❌ Sensitive |
| **Hierarchical** | Connectivity-based | ⚠️ Depends on Linkage | ⚠️ Depends |
| **DBSCAN** | Density-based | ✅ Excellent | ✅ Robust |

> **主要結論**：K-Means 適合處理結構簡單、密度均勻的球狀分群；而 DBSCAN 則在處理複雜形狀（如 Banana dataset）時表現最佳，無需預先指定群集數量。

