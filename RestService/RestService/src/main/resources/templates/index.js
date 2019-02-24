function myDrawArrow(){
	var canvas = document.getElementById('firstline');
	var ctx = canvas.getContext('2d');
	drawArrow(ctx, 150, 100, 400,100,30,30,10,'#f36');
}

function drawArrow(ctx, fromX, fromY, toX, toY,theta,headlen,width,color) { 
    theta = typeof(theta) != 'undefined' ? theta : 30;
	headlen = typeof(theta) != 'undefined' ? headlen : 10;
	width = typeof(width) != 'undefined' ? width : 1;
	color = typeof(color) != 'color' ? color : '#000'; 
	// 计算各角度和对应的P2,P3坐标 
	var angle = Math.atan2(fromY - toY, fromX - toX) * 180 / Math.PI,
    	angle1 = (angle + theta) * Math.PI / 180, angle2 = (angle - theta) * Math.PI / 180,
		topX = headlen * Math.cos(angle1),
		topY = headlen * Math.sin(angle1),
		botX = headlen * Math.cos(angle2),
		botY = headlen * Math.sin(angle2);
		ctx.save();
		ctx.beginPath();
		var arrowX = fromX - topX, 
		    arrowY = fromY - topY;
		ctx.moveTo(arrowX, arrowY);
		ctx.moveTo(fromX, fromY);
		ctx.lineTo(toX, toY);
		arrowX = toX + topX;
		arrowY = toY + topY;
		ctx.moveTo(arrowX, arrowY);
		ctx.lineTo(toX, toY);
		arrowX = toX + botX;
		arrowY = toY + botY;
		ctx.lineTo(arrowX, arrowY);
		ctx.strokeStyle = color;
		ctx.lineWidth = width;
		ctx.stroke();
		ctx.restore();
}