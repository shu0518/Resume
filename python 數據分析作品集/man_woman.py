import pandas as pd
import matplotlib.pyplot as plt

# 讀取資料，指定字符編碼為 Big5
df = pd.read_csv('0-cancer_file.csv', encoding='Big5')

# 資料篩選：選擇癌症發生數、性別、癌症診斷年和癌症別，只保留全國的資料
selected_columns = ['癌症診斷年', '性別', '癌症發生數', '癌症別']
df_selected = df[(df['癌症別'] != '全癌症') & (df['縣市別'] == '全國')][selected_columns]
# 資料分組：根據癌症別、性別、年份，將資料分組
df_grouped = df_selected.groupby(['性別', '癌症別', '癌症診斷年']).sum().reset_index()

# 選擇每個性別的前3多的癌症
top3_male = df_grouped[df_grouped['性別'] == '男'].groupby('癌症別').sum().nlargest(3, '癌症發生數').index
top3_female = df_grouped[df_grouped['性別'] == '女'].groupby('癌症別').sum().nlargest(3, '癌症發生數').index
print(top3_male)
print(top3_female)
# 繪圖
# 解決中文亂碼問題
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
# 解決負號無法正常顯示問題
plt.rcParams['axes.unicode_minus'] = False
# 繪製男性的趨勢圖
plt.figure(figsize=(16, 8))
for cancer_type in top3_male:
    df_cancer_type = df_grouped[(df_grouped['性別'] == '男') & (df_grouped['癌症別'] == cancer_type)]
    plt.plot(df_cancer_type['癌症診斷年'], df_cancer_type['癌症發生數'], label=f'男性 - {cancer_type}')

plt.title('全國男性癌症發生數成長前三名趨勢')
plt.xlabel('年份')
plt.ylabel('癌症發生數')
plt.legend()
plt.show()

# 繪製女性的趨勢圖
plt.figure(figsize=(16, 8))
for cancer_type in top3_female:
    df_cancer_type = df_grouped[(df_grouped['性別'] == '女') & (df_grouped['癌症別'] == cancer_type)]
    plt.plot(df_cancer_type['癌症診斷年'], df_cancer_type['癌症發生數'], label=f'女性 - {cancer_type}')

plt.title('全國女性癌症發生數成長前三名趨勢')
plt.xlabel('年份')
plt.ylabel('癌症發生數')
plt.legend()
plt.show()
