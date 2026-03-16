import React, { useState } from 'react';
import axios from 'axios';

const Login = ({ onLoginSuccess, onGoRegister }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        if (!username || username.trim() === '') {
            alert("请输入账号");
            return;
        }

        if (!password || password.trim() === '') {
            alert("请输入密码");
            return;
        }

        const params = new URLSearchParams();
        params.append('username', username);
        params.append('password', password);

        axios.post('http://localhost:8080/api/login', params, { withCredentials: true })
            .then(res => {
                if (res.data === "OK" || (res.data && res.data.code === 200)) {
                    onLoginSuccess(res.data.data);
                } else {
                    alert(res.data.data);
                }
            })
            .catch(err => {
                alert("服务器连接失败，请检查后端是否启动");
            });
    };

    return (
        <div className="reg-container">
            <div className="reg-form">
                <div className="header-bar">用户登录</div>

                <div className="form-body">
                    <p>
                        账号：
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </p>
                    <p>
                        密码：
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </p>
                    <div style={{marginTop: '20px', textAlign: 'center'}}>
                        <button className="btn-blue" onClick={handleLogin}>登录</button>
                        <button className="btn-blue" onClick={onGoRegister} style={{ marginLeft: '10px', backgroundColor: '#5bc0de', borderColor: '#46b8da' }}>注册</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;