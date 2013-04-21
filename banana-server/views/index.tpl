Oggi è: {{data}} <br/>
%for coda in coda:
<p>Mi chiamo {{coda.command}} e faccio {{cliente.parameter}} di parameter</p>
%end
<form action='/chiamami' method='POST'>
<input type='text' name='test'/>
<button type='submit'>Chiamami!</button>
</form>
