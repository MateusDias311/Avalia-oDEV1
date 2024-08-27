	<!DOCTYPE html>
	<html>
	<head>
	    <title>Gerar Relatório de Exames</title>
	</head>
	<body>
	    <h1>Gerar Relatório de Exames</h1>
	    <form action="relatorioExames" method="post">
	        <label for="dataInicial">Data Inicial:</label>
	        <input type="date" id="dataInicial" name="dataInicial" required>
	        
	        <label for="dataFinal">Data Final:</label>
	        <input type="date" id="dataFinal" name="dataFinal" required>
	        
	        <label for="formato">Formato:</label>
	        <select id="formato" name="formato" required>
	            <option value="html">HTML</option>
	            <option value="excel">Excel</option>
	        </select>
	        
	        <input type="submit" value="Gerar Relatório">
	    </form>
	</body>
	</html>
