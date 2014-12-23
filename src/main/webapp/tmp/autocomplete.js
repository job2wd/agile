var highlightindex = -1; //定义高亮显示索引
var timeOutId; //定义延迟时间

function addRealSearch(text_id) {
	realSearch(text_id);
}

function realSearch(text_id) {
	var $textInput = $("#" + text_id);
	var textInputOffset = $textInput.offset();
	
	//隐藏自动补全框,并定义css属性
    $("#auto_complete_toggle").hide()
              .css("position","absolute")
              .css("border","1px solid #DAE1E8")
              .css("top",textInputOffset.top + $textInput.height() + 5 + "px")
              .css("left",textInputOffset.left + "px")
              .width($textInput.width() + 2);
    
    $textInput.keyup(function(event){
    	var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        
        var $autoNode = $("#auto_complete_toggle");
        
        if(keyCode >= 48 && keyCode <= 57
        		|| keyCode == 190
        		|| keyCode == 189
        		|| keyCode == 191
        		|| keyCode == 8
        		|| keyCode == 46) {//由于是ip地址、子网、ip地址范围，所以只能输入“0-9数字”、“.”、“-”、“/”、退格、删除
        	
        	var val = $textInput.val();
        	
        	if(val && val.trim()) {
        		//ajax 请求实时搜索
        		 $.post("<c:url value='/di/policy_ajaxRealSearchIp.htm' />", {ip:val.trim()}, function(data){
        			$autoNode.html("");
        			
        			$.each(d, function(i){
        				var data = $.parseJSON(d);
        				var ip = data.ip;
        				var name = data.name;
        				
        				var $newDivNode = $("<div>").attr("id",i).attr("ip", ip);
                        $newDivNode.html(name).appendTo($autoNode);
                        
                        //添加光标进入事件,高亮节点
                        $newDivNode.mouseover(function(){
                            if(highlightindex != -1){
                                $("#auto").children("div")
                                          .eq(highlightindex)
                                          .css("background-color","white");
                            }
                            highlightindex = $(this).attr("id");
                            $(this).css("background-color","gray");
                        });
                        
                        //添加光标移出事件,取消高亮
                        $newDivNode.mouseout(function(){
                            $(this).css("background-color","white");
                        });
                        
                        //添加光标点击事件
                        $newDivNode.click(function(){                        
                        	$autoNode.hide();
                        	highlightindex = -1;
                        
                        	var comText = $(this).text();
                        	$textInput.val(comText);
                        });
                    });
        			
                    if(data.length > 0){
                    	$autoNode.show();
                    }else{
                    	$autoNode.hide();
                    	highlightindex = -1;
                    }
        		}, "json");        		
        	} else {
                $autoNode.hide();
                highlightindex = -1;
            }
        } else if(keyCode == 38){//按向上键,选中文字高亮
        	var $autoNodes = $autoNode.children("div");
        	
            if(highlightindex != -1) {
                $autoNodes.eq(highlightindex).css("background-color","white");
                highlightindex--;
            } else {
                highlightindex = $autoNodes.length - 1;
            }
            
            if(highlightindex == -1) {
                highlightindex = $autoNodes.length -1;
            }
            
            $autoNodes.eq(highlightindex).css("background-color","gray");
            
            var comText = $autoNode.hide().children("div").eq(highlightindex).text();
            $textInput.val(comText);
        } else if(keyCode == 40){//按向下键,选中文字高亮
        	var $autoNodes = $autoNode.children("div")
        	
            if(highlightindex != -1) {
                $autoNodes.eq(highlightindex).css("background-color","white");
            }
            
        	highlightindex++;
            
            if(highlightindex == $autoNodes.length) {
                highlightindex = 0;
            }
            
            $autoNodes.eq(highlightindex).css("background-color","gray");
            
            var comText = $autoNode.hide().children("div").eq(highlightindex).text();
            $textInput.val(comText);
        } else if(keyCode == 13){//按回车键，添加 IP
        	$autoNode.hide();
        	
        	$textInput.get(0).blur();
        	
        	if(text_id.startWith("src")) {
        		addIp(src_ip);
        	} else {
        		addIp(des_ip);
        	}        	
        }            
    });
}
