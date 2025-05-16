import pandas as pd

# 讀取資料，指定字符編碼為 Big5
df = pd.read_csv('0-cancer_file.csv', encoding='Big5')

# 資料篩選：選擇全國資料，性別為全，平均年齡低於30歲
df_filtered = df[(df['縣市別'] == '全國') & (df['性別'] == '全') & (df['平均年齡'] < 30)]

# 以癌症別分類，統計每個癌症別在全部年份中平均年齡低於30的次數
count_by_cancer_type = df_filtered['癌症別'].value_counts().reset_index(name='次數').rename(columns={'index': '癌症別'})

# 顯示統計結果
print("每個癌症別在全部年份中，平均年齡低於30的次數：")
print(count_by_cancer_type)

# 顯示各癌症別每筆資料的年份、癌症發生數、平均年齡、中位數
for cancer_type in count_by_cancer_type['癌症別']:
    print(f"\n{cancer_type} 詳細資料：")
    detailed_data = df[(df['癌症別'] == cancer_type) & (df['平均年齡'] < 30) & (df['性別'] == '全') & (df['縣市別'] == '全國')]
    print(detailed_data[['癌症診斷年', '癌症發生數', '平均年齡', '年齡中位數']].to_string(index=False))
