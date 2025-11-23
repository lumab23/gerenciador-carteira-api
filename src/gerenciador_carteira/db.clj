(ns gerenciador-carteira.db
  "Namespace para gerenciar o estado em memória (o atom).")

(defonce app-database
	(atom {
		:transacoes '()}))
