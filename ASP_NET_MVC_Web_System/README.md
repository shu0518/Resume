# Zoo-IMS (動物園區資訊管理系統)

> 一個基於 ASP.NET MVC 5 與原生 ADO.NET 開發的動物園區資訊與動物管理系統，支援動物分類管理、圖片上傳與互動式留言板功能。

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Last Updated](https://img.shields.io/badge/Last_Updated-2026.03.11-brightgreen)
![.NET Framework](https://img.shields.io/badge/.NET_Framework-4.7.2-purple.svg)

##  Key Features 

- 🦁 **動物與物種管理 (Animal & Species Management)**：基於 `Department` (物種) 與 `Employee` (動物) 的架構，提供完整的 CRUD 操作，並支援動物照片上傳與顯示。
- 💬 **互動式留言板 (Interactive Guestbook)**：內建遊客評論區，訪客可輕鬆留下評論，並支援動態修改與刪除功能。
- 🖼️ **首頁動態輪播 (Dynamic Carousel)**：首頁提供由資料庫動態驅動的圖片輪播系統 (`ImgCarouselDBService`)，便於後台抽換園區最新活動。
- ⚡ **原生 ADO.NET 存取機制**：系統不依賴龐大的 ORM，採用輕量級的原生 ADO.NET (`SqlConnection`, `SqlCommand`) 配合自建的 Service 層與 ViewModels，確保資料庫存取的高效與直接。

##  Tech Stack

- **框架**: ASP.NET MVC 5 (.NET Framework 4.7.2)
- **後端語言**: C#
- **資料庫存取**: ADO.NET (SQL Server)
- **前端呈現**: Razor Views (`.cshtml`), HTML5, CSS3

##  Quick Start 

### 1. 取得專案

```bash
git clone https://github.com/shu0518/Resume.git
cd Resume/ASP_NET_MVC_Web_System/s1411031038-NETFinal
```

### 2. 環境設定與安裝

1. 確認已安裝 **Visual Studio 2019/2022**，並包含 `ASP.NET 和網頁開發` 工作負載。
2. 確認已安裝 **SQL Server** 或 **LocalDB**。
3. 雙擊開啟方案檔 `s1411031038-NETFinal.sln`。
4. 建立對應的 SQL Server 資料庫 (`s1411031038_NETFinal`) 以及所需資料表 (`Dep`, `Emp`, `Guestbooks` 等)。

### 3. 執行專案

1. 在 Visual Studio 中按 `F5` 或點擊 **IIS Express** 啟動編譯並執行。
2. 瀏覽器將自動���啟系統首頁（預設網址通常為 `http://localhost:port/`）。

## Project Structure 

```text
s1411031038-NETFinal/
├── Controllers/       # 處理 HTTP 請求的控制器 (包含 FinalController, DepEmpController 等)
├── Models/            # 資料實體模型 (Department, Employee, Guestbooks 等)
├── ViewModels/        # 用於視圖與控制器間傳遞資料的複合模型
├── Services/          # 核心商業邏輯與資料庫存取層 (DB Services, 封裝 ADO.NET 操作)
├── Views/             # Razor 網頁視圖 (含留言板、動物園區介紹等介面)
├── images/            # 存放動物照片與系統圖片的上傳目錄
├── Global.asax        # 應用程式全域設定與路由註冊
└── Web.config         # 環境變數與連線字串等系統設定
```

## Environment Variables 

本專案使用 `Web.config` 管理連線字串，請在部署或本機執行前，確認 `<connectionStrings>` 區段的資料庫配置是否符合你的環境：

```xml
<connectionStrings>
    <!-- 請根據你的 SQL Server 實例修改 Server, User ID 與 Password -->
    <add name="s1411031038_NETFinal" 
         connectionString="Persist Security Info=False;Integrated Security=true;Server=localhost;Initial Catalog=s1411031038_NETFinal;User ID=;Password=;" 
         providerName="System.Data.SqlClient" />
</connectionStrings>
```

## API / Routing Reference 

系統採用標準的 MVC 路由 `/{Controller}/{Action}/{Id}`，以下為主要功能的路徑對照表：

| 模組 | HTTP Method | 路由 / Action | 功能描述 |
| :--- | :--- | :--- | :--- |
| **首頁展示** | `GET` | `/Final/首頁` | 載入首頁與動態輪播圖 |
| **留言板** | `GET` | `/Final/評論區` | 取得遊客留言列表 |
| **留言板** | `POST` | `/Final/Create` | 提交新的遊客留言 |
| **動物園區** | `GET` | `/DepEmp/園區動物介紹` | 根據物種 (depid) 顯示該區動物導覽 |
| **動物管理** | `GET` | `/DepEmp/動物管理` | 顯示後台動物管理清單 |
| **動物管理** | `POST`| `/DepEmp/Create` | 透過上傳表單新增動物資料與照片 |
| **動物管理** | `POST`| `/DepEmp/UpdateEmp` | 編輯或更新特定動物資訊 |
