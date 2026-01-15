# Data Mining Assignment 1: Decision Tree Classification

本專案的主要目標是實作並比較四種不同的決策樹演算法（ID3, C4.5, CART, C5.0），並進行收入預測（分類任務）。

## 📂 Project Structure

本專案包含以下核心程式碼與分析報告：

- **`ID3.ipynb`**: 
  - 實作 ID3 演算法。
  - 使用 **Information Gain (資訊獲利)** 作為切割準則。
  - 包含針對離散型特徵的處理。
  
- **`C4.5.ipynb`**: 
  - ID3 的改進版本。
  - 使用 **Gain Ratio (獲利比率)** 解決多值屬性偏誤問題。
  - 能夠處理連續型數值特徵。

- **`CART.ipynb`**: 
  - Classification and Regression Trees 實作。
  - 使用 **Gini Impurity (吉尼不純度)** 進行二元切割。
  - 包含模型訓練與效能評估指標 (Precision, Recall, F1-score)。

- **`C5.0.ipynb` (🌟 Highlight)**: 
  - 實作了 **C5.0** 演算法及其 **Boosting** 機制。
  - **特色功能**：
    - 實作 `C50DecisionTree` 與 `C50Boosted` 類別。
    - 支援 **Boosting (Ensemble Learning)** 以提升預測準確率。
    - 包含完整的資料前處理流程 (缺失值填補、特徵編碼)。
    - 使用 Graphviz 輸出決策樹視覺化圖表 (`C5.0_Tree_*.png`)。

## 📊 資料集 (Dataset)

- **來源**: [Adult Data Set (Census Income)](https://archive.ics.uci.edu/ml/datasets/adult)
- **目標**: 根據年齡、職業、教育程度等特徵，預測年收入是否超過 $50K。
- **特徵工程**:
  - 處理缺失值 (Missing Values)：使用眾數 (Mode) 填補。
  - 數值特徵處理：離散化 (Discretization) 或正規化。

## 🚀 執行結果 (Results)

比較不同演算法在測試集上的表現，其中 **C5.0 (with Boosting)** 表現最佳：

| Algorithm | Metric | Performance |
|-----------|--------|-------------|
| **C5.0 (Boosted)** | Accuracy | **~86.40%** |
| **CART** | Accuracy | ~85.55% |
| **C4.5** | Accuracy | (See Notebook) |
| **ID3** | Accuracy | (See Notebook) |

