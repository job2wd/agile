该目录用于存放系统用到的 dll 库（windows）或 so 库（linux）。
		
注：
1. ZMQ java 的集成，除了按照官方的方式安装外，另外一种方式是：
	(1) 将该目录下的所有和 zmq 相关的 so 库拷贝到  /usr/local/lib 目录
	(2) 在 tomcat catalina.sh 中的 JAVA_OPTS 参数中添加  -Djava.library.path=/usr/local/lib
		或 
			在 ~/.bash_profile 中加入 export LD_LIBRARY_PATH=/usr/local/lib
	           或
			通过设置系统变量 java.library.path 的值，加载该目录。如 sigar 的集成方式 （2）
	        
2. Sigar java 的集成，如如下三种方式：
	(1) libsigar-*.so 文件放到 Linux /usr/lib64/目录下
        sigar-*.dll 文件放到 JAVA_HOME/bin 目录下（Windows下测试用）
    (2) 通过设置系统变量 java.library.path 的值，加载该目录，以便让 sigar api 能够找到对应的 dll 或 so 库。
    	可参考：org.hyperic.sigar.test.SigarTest.initLibraryPath()
	(3) 如 ZMQ java 的集成方式（2）


	