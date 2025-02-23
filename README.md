# 🎯 **Gerenciamento de Quadros de Tarefas (Task Board Management)**

Este projeto foi criado como parte do **Desafio DIO Decola Tech 2025**.  
O sistema implementa um gerenciador de **boards** (quadros de tarefas), permitindo organizar e acompanhar o fluxo de trabalho por meio de colunas personalizadas e regras específicas de movimentação de cartões (cards).

---

## 🚀 **Principais Funcionalidades**

### 📋 **Regras dos Boards**
- Cada **board**:
  - Deve ter um **nome**.
  - Contém **pelo menos 3 colunas** obrigatórias:
    - **Inicial**: onde os cards começam.
    - **Concluído**: para tarefas finalizadas.
    - **Cancelado**: para tarefas descartadas.
  - Colunas adicionais do tipo **pendente** podem ser adicionadas livremente.

- As **colunas** possuem:
  - Nome.
  - Ordem de aparição.
  - Tipo: Inicial | Pendente | Final | Cancelamento.

- Regras de disposição:
  1. Somente uma coluna de cada tipo especial (Inicial, Final, Cancelamento).
  2. Ordem das colunas:
     - Inicial → Pendentes → Final → Cancelamento.

### 🗂️ **Regras dos Cards**
- Cada **card** inclui:
  - Título.
  - Descrição.
  - Data de criação.
  - Status: Bloqueado 🔒 ou Desbloqueado 🔓.

- Movimentação:
  - Cards seguem a ordem das colunas, sem pular etapas.
  - Podem ser movidos diretamente para a coluna de **Cancelamento** (exceto se estiverem na coluna Final).
  - **Cards bloqueados**:
    - Não podem ser movidos.
    - Exigem um motivo para bloqueio/desbloqueio.

---

## 🛠️ **Menu de Ações no Board**
- ➡️ Mover um card para a próxima coluna.  
- ❌ Cancelar um card (mover para a coluna de cancelamento).  
- ➕ Criar um novo card.  
- 🔒 Bloquear um card (com motivo).  
- 🔓 Desbloquear um card (com motivo).  
- 🔚 Fechar o board.  

---

## 📅 **Histórico de Movimentação (Opcional)**
- Cada movimentação registra:
  - **Data e hora de entrada** em cada coluna.  
  - **Data e hora de movimentação** entre colunas.  

---


