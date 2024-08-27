<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Relatório de Exames Realizados</title>
</head>
<body>
    <h1>Relatório de Exames Realizados</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome do Exame</th>
                <th>Data de Realização</th>
                <!-- Outros campos -->
            </tr>
        </thead>
        <tbody>
            <c:forEach var="exame" items="${exames}">
                <tr>
                    <td>${exame.id}</td>
                    <td>${exame.nome}</td>
                    <td>${exame.dataRealizacao}</td>
                    <!-- Outros campos -->
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
