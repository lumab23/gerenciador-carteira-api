(ns gerenciador-carteira.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [gerenciador-carteira.services.acao-service :as acao-service]
            [gerenciador-carteira.services.transacao-service :as transacao-service]
            [gerenciador-carteira.services.carteira-service :as carteira-service]))

(defroutes app-routes
  (GET "/" [] "Bem-vindo ao Gerenciador de Carteira de Ações!")
  
  ;; consultar os dados de uma ação
  (GET "/acao/:ticker" [ticker]
    (try
      {:status 200
       :body (acao-service/get-infomacao-acao ticker)}
      (catch Exception e
        {:status 404
         :body {:error (.getMessage e)}})))

  ;; registrar uma compra
  (POST "/transacoes/compra" request
    (let [dados (:body request)
          ticker (:ticker dados)
          data (:data dados)
          qtd (:qtd dados)
          preco (:preco dados)] 
      (transacao-service/registrar-compra ticker data qtd preco)
      {:status 201 :body {:mensagem "Compra registrada com sucesso"}}))
  
  ;; registrar uma venda
  (POST "/transacoes/venda" request
    (let [dados (:body request)
          ticker (:ticker dados)
          data (:data dados)
          qtd (:qtd dados)
          preco (:preco dados)]
      (transacao-service/registrar-venda ticker data qtd preco)
      {:status 201 :body {:mensagem "Venda registrada com sucesso"}}))

  ;; exibir Extrato
  (GET "/transacoes" []
    {:status 200
     :body (carteira-service/listar-transacoes)})

  ;; exibir Saldo
  (GET "/carteira/saldo" []
    {:status 200
     :body {:saldo (carteira-service/calcular-saldo)}})

  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true}) 
      (wrap-json-response)               
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false)))) 
