# Taiwan Cancer Data Analysis Portfolio

> 一個基於 Python 與 Pandas 的台灣癌症數據分析與視覺化專案，提供性別、年齡及癌別的深度資料探索與趨勢追蹤。

![Last Updated](https://img.shields.io/badge/Last_Updated-2026.03.11-brightgreen)
[![Python Version](https://img.shields.io/badge/python-3.8%2B-blue.svg)](https://www.python.org/)
[![Pandas](https://img.shields.io/badge/pandas-data%20analysis-150458.svg?logo=pandas)](https://pandas.pydata.org/)
[![Matplotlib](https://img.shields.io/badge/matplotlib-visualization-ffffff.svg?logo=python)](https://matplotlib.org/)

---

## Key Features

- **歷年癌症發生趨勢追蹤**：篩選全國性資料，統計男女癌症發生數量的歷年變化，並自動輸出時間序列趨勢圖，直觀呈現長期變化 (`total.py`)。
- **高風險癌別排名與對比**：針對不同性別，自動計算並萃取出發生率前三高的癌症種類，並繪製多重折線圖來對比各癌別的成長趨勢 (`man_woman.py`)。
- **年輕族群罹癌特徵分析**：專注於平均年齡低於 30 歲的年輕族群，深入剖析各癌別的發生頻率、年齡中位數與詳細歷史分佈 (`young.py`)。

## 🛠 Tech Stack

- **程式語言**：Python 3
- **資料處理與清洗**：Pandas
- **數據視覺化**：Matplotlib

## Quick Start

### 1. 取得專案程式碼
```bash
git clone https://github.com/shu0518/Resume.git
cd Resume/Python_Data_Analysis_Portfolio
```

### 2. 安裝套件
請確保你的環境已經安裝 Python，並執行以下指令安裝必要套件：
```bash
pip install pandas matplotlib
```

### 3. 執行分析
本專案提供三種不同維度的分析程式碼，可根據需求獨立執行：
```bash
# 查看男女歷年總癌症發生數與趨勢圖
python total.py

# 查看男女前三名高發癌別的詳細趨勢圖
python man_woman.py

# 查看平均年齡低於 30 歲之年輕族群罹癌統計
python young.py
```

## Project Structure

```text
Python_Data_Analysis_Portfolio/
├── 0-cancer_file.csv                  # 資料集（包含全國各縣市、年份、癌別等原始資料）
├── total.py                           # 歷年男女癌症總發生數分析與視覺化
├── man_woman.py                       # 男女前三高風險癌別趨勢分析與視覺化
├── young.py                           # 低於30歲年輕族群之罹癌特徵統計
├── 女性癌症前三名.png                 # 分析輸出結果圖表
├── 男性癌症前三名.png                 # 分析輸出結果圖表
├── 癌症趨勢圖.png                     # 分析輸出結果圖表
└── 112跨平台程式設計-期末報告.docx    # 專案期末書面報告文件
```

## Environment Variables

本專案目前採用本地 CSV 檔案（`0-cancer_file.csv`）進行靜態分析，**無需配置額外的 `.env` 環境變數**。

*註：為確保 Matplotlib 能正常渲染中文字體與負號，腳本內已內建 `Microsoft JhengHei` (微軟正黑體) 的字型設定支援。如果你使用 Mac/Linux 環境，可能需要根據系統支援的字體修改 `plt.rcParams['font.sans-serif']`。*
