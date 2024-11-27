<!-- src/components/chatbot/ChatbotPopup.vue -->
<template>
    <div id="chatbot-popup" :class="{ open: isOpen }">
      <div class="chatbot-header" @click="togglePopup">
        <h3>챗봇 도우미</h3>
        <span>{{ isOpen ? "▲" : "▼" }}</span>
      </div>
      <transition name="fade">
        <div v-if="isOpen" class="chatbot-body">
          <div class="chat-messages" ref="chatMessages">
            <div
              v-for="(message, index) in messages"
              :key="index"
              :class="['message', message.type]"
            >
              <div class="message-content">
                <span v-if="message.type === 'bot'" class="message-avatar">🏕️</span>
                <span v-if="message.type === 'user'" class="message-avatar">🤔</span>
                <div class="message-text">{{ message.text }}</div>
              </div>
            </div>
          </div>
          <div class="chat-input">
            <input
              v-model="userInput"
              @keyup.enter="sendMessage"
              placeholder="메시지를 입력하세요..."
            />
            <button @click="sendMessage">전송</button>
          </div>
        </div>
      </transition>
    </div>
  </template>
  
  <script setup>
  import { ref, nextTick } from "vue";
  import axios from "axios";
  
  const isOpen = ref(false);
  const userInput = ref("");
  const messages = ref([]);
  
  // 챗봇 환영 메시지
  const welcomeMessage = "안녕하세요! 캠핑 관련 궁금한 게 있으면 저에게 물어봐 주세요!";
  
  const togglePopup = () => {
    isOpen.value = !isOpen.value;
    if (isOpen.value && messages.value.length === 0) {
      messages.value.push({ text: welcomeMessage, type: "bot" });
    }
  };
  
  const sendMessage = async () => {
    const input = userInput.value.trim();
    if (input === "") return;
  
    messages.value.push({ text: input, type: "user" });
    userInput.value = "";
  
    try {
      const response = await axios.post("/chatbot", { message: input });
      messages.value.push({ text: response.data.reply, type: "bot" });
    } catch (error) {
      console.error("Chatbot API 호출 실패:", error);
      messages.value.push({ text: "오류가 발생했습니다. 다시 시도해주세요.", type: "bot" });
    } finally {
      nextTick(() => scrollToBottom());
    }
  };
  
  const scrollToBottom = () => {
    const chatMessages = document.querySelector(".chat-messages");
    if (chatMessages) {
      chatMessages.scrollTop = chatMessages.scrollHeight;
    }
  };
  </script>
  
  <style scoped>
  #chatbot-popup {
    position: fixed;
    bottom: 20px;
    right: 20px;
    width: 300px;
    max-width: 90%;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    z-index: 1000;
    overflow: hidden;
    transition: height 0.3s ease, transform 0.3s ease;
  }
  
  .chatbot-header {
    background: #4a90e2;
    color: white;
    padding: 12px 16px;
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .chatbot-header h3 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
  }
  
  .chatbot-header span {
    font-size: 16px;
    transition: transform 0.3s;
  }
  
  .chatbot-body {
    display: flex;
    flex-direction: column;
    height: 350px;
    background-color: #f9f9f9;
  }
  
  .chat-messages {
    flex: 1;
    padding: 10px 16px;
    overflow-y: auto;
  }
  
  .message {
    display: flex;
    margin-bottom: 10px;
  }
  
  .message-content {
    display: flex;
    align-items: flex-end;
  }
  
  .message-avatar {
    font-size: 20px;
    margin-right: 8px;
  }
  
  .message-text {
    max-width: 70%;
    padding: 8px 12px;
    border-radius: 16px;
    background-color: #e1ffc7;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    font-size: 13px;
    line-height: 1.4;
    word-wrap: break-word;
  }
  
  .message.bot .message-text {
    background-color: #ffffff;
    border: 1px solid #ddd;
  }
  
  .message.user {
    justify-content: flex-end;
  }
  
  .message.user .message-text {
    background-color: #dcf8c6;
  }
  
  .chat-input {
    display: flex;
    padding: 10px 16px;
    background-color: #ffffff;
    border-top: 1px solid #ddd;
  }
  
  .chat-input input {
    flex: 1;
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 20px;
    outline: none;
    font-size: 13px;
    transition: border-color 0.3s;
  }
  
  .chat-input input:focus {
    border-color: #4a90e2;
  }
  
  .chat-input button {
    margin-left: 8px;
    padding: 0 16px;
    background-color: #4a90e2;
    border: none;
    border-radius: 20px;
    color: white;
    font-size: 13px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .chat-input button:hover {
    background-color: #357ab8;
  }
  
  /* 애니메이션 */
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s;
  }
  .fade-enter-from, .fade-leave-to {
    opacity: 0;
  }
  
  /* 반응형 디자인 */
  @media (max-width: 480px) {
    #chatbot-popup {
      width: 90%;
      right: 5%;
      bottom: 10px;
    }
  
    .chatbot-header h3, .chatbot-header span {
      font-size: 14px;
    }
  
    .chatbot-body {
      height: 300px;
    }
  
    .message-avatar {
      font-size: 18px;
      margin-right: 6px;
    }
  
    .message-text {
      max-width: 65%;
      padding: 6px 10px;
      font-size: 12px;
    }
  
    .chat-input input {
      padding: 6px 10px;
      font-size: 12px;
    }
  
    .chat-input button {
      padding: 0 12px;
      font-size: 12px;
    }
  }
  </style>
  