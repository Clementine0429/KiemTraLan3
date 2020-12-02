<?php
	require_once('lib/db.php');
	if(isset($_GET['Status'])){
		$r=DP::run_query('SELECT `word`, `definition` FROM `table_dictionary` where Status=? limit 0,5',[$_GET['Status']],2);
		echo json_encode($r);
	}
	
	if(isset($_GET['word'])){
		$r=DP::run_query("SELECT `word`, `definition` FROM `table_dictionary` WHERE `word` like concat(?, '%')",[$_GET['word']],2);
		echo json_encode($r);
	}
?>
