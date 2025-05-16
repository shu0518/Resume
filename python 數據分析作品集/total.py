import pandas as pd
import matplotlib.pyplot as plt

# 讀取資料，指定字符編碼為 Big5
df = pd.read_csv('0-cancer_file.csv', encoding='Big5')

# 資料篩選：選擇癌症發生數、性別和癌症診斷年
selected_columns = ['癌症診斷年', '性別', '癌症發生數']
df_selected = df[(df['癌症別'] != '全癌症') & (df['縣市別'] == '全國')][selected_columns]
# 資料分組：根據性別、年份，將資料分組
df_grouped = df_selected.groupby(['性別', '癌症診斷年']).sum().reset_index()

# 顯示前三年的統計結果
for year in df_grouped['癌症診斷年'].unique()[:3]:
    total_male_cancer = df_grouped[(df_grouped['性別'] == '男') & (df_grouped['癌症診斷年'] == year)]['癌症發生數'].sum()
    total_female_cancer = df_grouped[(df_grouped['性別'] == '女') & (df_grouped['癌症診斷年'] == year)]['癌症發生數'].sum()
    total_cancer = total_male_cancer+total_female_cancer

    print(f'統計年份: {year}')
    print(f'  男性癌症發生數總和: {total_male_cancer:.0f}')
    print(f'  女性癌症發生數總和: {total_female_cancer:.0f}')
    print(f'  總癌症發生數總和: {total_cancer:.0f}')
    print('-' * 30)

# 繪圖
# 解決中文亂碼問題
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
# 解決負號無法正常顯示問題
plt.rcParams['axes.unicode_minus'] = False

# 時間序列視覺化：繪製男女的癌症發生數隨時間的變化趨勢
plt.figure(figsize=(12, 8))

# 繪製男性的趨勢
df_male = df_grouped[df_grouped['性別'] == '男']
plt.plot(df_male['癌症診斷年'], df_male['癌症發生數'], 'o-', label='男性')

# 繪製女性的趨勢
df_female = df_grouped[df_grouped['性別'] == '女']
plt.plot(df_female['癌症診斷年'], df_female['癌症發生數'], 'o-', label='女性')

plt.title('男女癌症發生數隨時間變化趨勢')
plt.xlabel('年份')
plt.ylabel('癌症發生數')
plt.legend()
plt.grid(True)  # 加入網格線
plt.show()
