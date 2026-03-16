import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MovieTable = ({ onBookSuccess }) => {
    const [movies, setMovies] = useState([]);
    const [booking, setBooking] = useState({ sid: '', uid: '', count: '' });

    const axiosConfig = { withCredentials: true };

    const loadData = () => {
        axios.get('http://localhost:8080/api/movies')
            .then(res => {
                if (res.data.code === 200) {
                    setMovies(res.data.data || []);
                } else {
                    console.error(res.data.message);
                    setMovies([]);
                }
            })
            .catch(err => {
                console.error("加载场次失败", err);
                setMovies([]);
            });
    };

    useEffect(() => { loadData(); }, []);

    const handleBook = () => {
        if (!booking.sid || !booking.count) {
            alert("请完整填写场次和票数");
            return;
        }

        const params = new URLSearchParams();
        params.append('sid', booking.sid);
        params.append('uid', booking.uid || "Guest");
        params.append('count', booking.count);

        axios.post('http://localhost:8080/api/book', params, axiosConfig)
            .then(res => {
                if (res.data.code === 200) {
                    alert(res.data.message || "购票成功！");
                    loadData();
                    if (onBookSuccess) onBookSuccess();
                } else {
                    alert(res.data.message);
                }
            })
            .catch(err => {
                alert("购票异常，请检查登录状态");
            });
    };

    return (
        <div style={{ padding: '20px' }}>
            <div className="header-bar">--- 可售电影场次安排如下 ---</div>
            {Array.isArray(movies) && movies.map(m => (
                <div key={m.movieId} style={{ marginBottom: '15px', borderBottom: '1px solid #444', paddingBottom: '10px' }}>
                    <p>场次: {m.movieId}</p>
                    <p>电影：{m.movieName}</p>
                    <p>放映时间：{m.movieTime}</p>
                    <p>余票数量: <span style={{ color: m.ticketsAvailable < 10 ? 'red' : 'green' }}>{m.ticketsAvailable}</span></p>
                </div>
            ))}

            <div className="header-bar" style={{ marginTop: '30px' }}>购票登记界面</div>
            <div style={{ backgroundColor: '#333', color: '#fff', padding: '15px', borderRadius: '5px' }}>
                <p>请输入订票信息:</p>
                <div style={{ marginBottom: '5px' }}>
                    <label>放映场次: </label>
                    <input type="text" value={booking.sid} onChange={e => setBooking({...booking, sid: e.target.value})} />
                </div>
                <div style={{ marginBottom: '5px' }}>
                    <label>用户ID: </label>
                    <input type="text" value={booking.uid} onChange={e => setBooking({...booking, uid: e.target.value})} />
                </div>
                <div style={{ marginBottom: '5px' }}>
                    <label>订购票数: </label>
                    <input type="number" value={booking.count} onChange={e => setBooking({...booking, count: e.target.value})} />
                </div>
                <button onClick={handleBook} style={{ marginTop: '10px', padding: '5px 15px', cursor: 'pointer' }}>确认购票</button>
            </div>
        </div>
    );
};

export default MovieTable;