(ns gerenciador-carteira.integration
	"namespace para integração com serviços externos"
	(:require [clj-http.client :as client]
			  [cheshire.core :as json]))

(defn buscar-informacao-acao
	"buscar dados na api da brapi"
	[ticker]

	(try
		(let [url (str "https://brapi.dev/api/quote/" ticker)
			resposta (client/get url {:accept :json})
			body (json/parse-string (:body resposta) true)
			acoes-dados (first (:results body))
			]

		(when acoes-dados
			{:ultimo-preco (:regularMarketPrice acoes-dados)
			 :nome (:longName acoes-dados)
			 :codigo (:symbol acoes-dados)
			 :maximo (:regularMarketDayHigh acoes-dados)
			 :minimo (:regularMarketDayLow acoes-dados)
			 :abertura (:regularMarketOpen acoes-dados)}))

		(catch Exception e
			(println (str "Erro ao buscar dados da ação " ticker ": " (.getMessage e)))
			nil)))
