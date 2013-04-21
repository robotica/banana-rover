<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <!-- Bootstrap -->
        <link href="/file/css/bootstrap.css" rel="stylesheet" media="screen">
        
    </head>
    
    <body>
        <h1>ciao</h1>
        %for coda in queue:
			<p>command {{coda.command}} e faccio {{coda.parameter}} parameter</p>
		%end
	<form action='/addcliente', method="POST">
  		<input type="text" name="command">
  		<input type="text" name="parameter">
  		<button type="submit">
	</form>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="/file/js/bootstrap.js"></script>
    </body>
</html>
