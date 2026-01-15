# Data Mining Project 2: Regression Prediction

本專案的目標是解決連續數值的預測問題，使用經典的 **Boston Housing Dataset** 來預測波士頓地區的房價。

實作並比較四種不同類型的演算法（KNN, SVR, Random Forest, XGBoost），並透過交叉驗證（K-Fold）來評估模型的穩定性。

## 📂 專案結構

本專案包含以下核心程式碼與資料集：

### 核心模型
- **`KNN_regressor.ipynb`**:
  - 使用 **K-Nearest Neighbors** 進行迴歸預測。
  - 包含資料標準化（Standardization）以優化距離計算。
  
- **`SVR.ipynb`**:
  - 使用 **Support Vector Regression**。
  - 探討不同核函數（Kernel functions）對預測的影響。

- **`RandomForest.ipynb`**:
  - 使用 **Random Forest**（Bagging 集成學習）。
  - 分析特徵重要性（Feature Importance），找出影響房價的關鍵因素。

- **`XGBoost.ipynb`**:
  - 使用 **XGBoost**（Gradient Boosting）。
  - 比較其與傳統機器學習模型的效能差異。

### 進階實驗 (🌟 Highlight)
- **`XGBoost_kfold.ipynb`**:
  - 使用 **K-Fold Cross-Validation** 進行模型驗證。
  - 透過多折驗證確保模型沒有 Overfitting，並取得更客觀的平均誤差值。

### 資料集
- **`housing.csv`**:
  - 經典的波士頓房價資料集。
  - **目標變數 (Target)**: `MEDV` (自有住宅房價中位數)。
  - **特徵 (Features)**: 包含犯罪率 (`CRIM`)、房間數 (`RM`)、屋齡 (`AGE`) 等 13 個特徵。

## 🚀 實驗結果 (Results)

透過比較 RMSE (均方根誤差)、MAPE (平均絕對百分比誤差) 與 R² Score，實驗結果顯示 **XGBoost** 與 **Random Forest** 在此資料集上表現最佳。

| Model | RMSE | MAPE (%) | R² Score | Note |
|-------|------|----------|----------|------|
| **XGBoost (K-Fold)** | *(Fill later)* | *(Fill later)* | *(Fill later)* | Best Stability |
| **XGBoost** | *(Fill later)* | *(Fill later)* | *(Fill later)* | Best Accuracy |
| **Random Forest** | *(Fill later)* | *(Fill later)* | *(Fill later)* | High Interpretability |
| **SVR** | *(Fill later)* | *(Fill later)* | *(Fill later)* | |
| **KNN** | *(Fill later)* | *(Fill later)* | *(Fill later)* | Baseline |

> 註：詳細數值請參考各 Notebook 的輸出結果。

## 🛠️ 技術細節 (Technical Details)

- **資料前處理 (Preprocessing)**:
  - 缺失值檢查。
  - 特徵縮放 (Feature Scaling)：針對 KNN 與 SVR 使用 `StandardScaler`。
- **評估指標 (Metrics)**:
  - **RMSE**: 衡量預測值與真實值的平均誤差程度。
  - **MAPE**: 衡量誤差的百分比，較易於商業解釋。
  - **R²**: 解釋模型對變異數的解釋能力。

## 🔧 環境需求 (Requirements)

執行本專案需要以下 Python 套件：

```bash
pip install pandas numpy scikit-learn matplotlib seaborn xgboost
