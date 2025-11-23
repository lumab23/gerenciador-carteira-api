(ns gerenciador-carteira.services.acao-service
	"serviço de negócio para obter dados de ações"
	(:require [gerenciador-carteira.integration :as integration]))

(defn get-infomacao-acao [ticker]
	(let [info (integration/buscar-informacao-acao ticker)]
		(if info
			info
			(throw (Exception. (str "Ação " ticker " não encontrada."))))))
