# Resume
履歷用

以android studio開發之app 

1.前端資料夾：front_end1104/app/src/main/res/layout

2.部分後端：front_end1104/app/src/main/java/com/example/a0731

訓練模型：YOLOv11m-seg

資料夾：window_detection

ultralytics hub上有現成的基礎模型可以使用 專題製作採用此方式訓練 以下附上若用本機訓練的簡易流程

	1.環境準備
	
		Python 3.8 或更新版本
		
		CUDA Toolkit 和 cuDNN
		
		與 GPU 驅動相容的 PyTorch 
	
	2.數據集準備
 
		使用labelme標註影像 至少6個點 越詳細標出物件輪廓訓練效果越好

 		將數據按照訓練、驗證和測試集分配 比例可為80/10/10 
	 
	3.模型加載與開始訓練
 
		選擇 YOLO模型
	
	 	調整參數
